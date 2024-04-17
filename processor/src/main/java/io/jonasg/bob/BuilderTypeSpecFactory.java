package io.jonasg.bob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import io.jonasg.bob.definitions.ConstructorDefinition;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.GenericParameterDefinition;
import io.jonasg.bob.definitions.ParameterDefinition;
import io.jonasg.bob.definitions.SimpleTypeDefinition;
import io.jonasg.bob.definitions.TypeDefinition;

public class BuilderTypeSpecFactory {

	protected final ConstructorDefinition constructorDefinition;

	protected final TypeDefinition typeDefinition;

	protected final Buildable buildable;

	protected final List<BuildableField> buildableFields;

	private final Types typeUtils;

	private String builderTypeName(TypeDefinition source) {
		return Formatter.format("$typeName$suffix", source.typeName(), "Builder");
	}

	protected BuilderTypeSpecFactory(TypeDefinition typeDefinition, Buildable buildable, Types typeUtils) {
		this.typeDefinition = typeDefinition;
		this.buildable = buildable;
		this.constructorDefinition = extractConstructorDefinitionFrom(typeDefinition);
		this.buildableFields = extractBuildableFieldsFrom(typeDefinition);
		this.typeUtils = typeUtils;
	}

	private List<BuildableField> extractBuildableFieldsFrom(TypeDefinition typeDefinition) {
		var fieldNames = typeDefinition.fields().stream()
				.map(FieldDefinition::name)
				.toList();
		List<ParameterDefinition> eligibleConstructorParams = this.constructorDefinition.parameters()
				.stream()
				.filter(p -> fieldNames.contains(p.name()))
				.toList();
		Stream<BuildableField> constructorBuildableFields = this.constructorDefinition.parameters()
				.stream()
				.filter(p -> fieldNames.contains(p.name()))
				.map(p -> BuildableField.fromConstructor(p.name(), p.type()));
		Stream<BuildableField> setterBuildableFields = this.typeDefinition.getSetterMethods()
				.stream()
				.filter(setter -> !eligibleConstructorParams
						.contains(new ParameterDefinition(setter.type(), setter.field().name())))
				.map(p -> {
					boolean fieldIsMandatory = Arrays.stream(this.buildable.mandatoryFields())
							.anyMatch(f -> Objects.equals(f, p.field().name()))
							|| p.field().isAnnotatedWith(Buildable.Mandatory.class);
					return BuildableField.fromSetter(p.field().name(), fieldIsMandatory, p.methodName(), p.type());
				});
		return Stream.concat(constructorBuildableFields, setterBuildableFields).toList();
	}

	private ConstructorDefinition extractConstructorDefinitionFrom(TypeDefinition typeDefinition) {
		var buildableConstructors = typeDefinition.constructors().stream()
				.filter(c -> c.isAnnotatedWith(Buildable.Constructor.class))
				.toList();
		if (buildableConstructors.size() > 1) {
			throw new IllegalArgumentException("Only one constructor can be annotated with @Buildable.Constructor");
		}
		if (buildableConstructors.isEmpty()) {
			return typeDefinition.constructors().stream()
					.max(Comparator.comparingInt(c -> c.parameters().size())).orElseThrow();
		} else {
			return buildableConstructors.get(0);
		}
	}

	public TypeSpec typeSpec() {
		TypeSpec.Builder builder = TypeSpec.classBuilder(builderTypeName(this.typeDefinition))
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		if (!this.typeDefinition.genericParameters().isEmpty())
			builder.addTypeVariables(toTypeVariableNames(this.typeDefinition));
		builder.addMethods(generateSetters());
		builder.addFields(generateFields());
		builder.addMethod(generateBuildMethod());
		builder.addMethod(generateConstructor());
		if (!this.typeDefinition.genericParameters().isEmpty()) {
			builder.addMethod(of());
		}
		return builder.build();
	}

	private List<MethodSpec> generateSetters() {
		return this.buildableFields.stream()
				.filter(this::notExcluded)
				.map(this::generateSetterForField)
				.toList();
	}

	protected MethodSpec generateSetterForField(BuildableField field) {

		var builder = MethodSpec.methodBuilder(setterName(field.fieldName()))
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType())
				.addParameter(TypeName.get(field.type()), field.fieldName());
		if (field.isConstructorArgument() && isAnEnforcedConstructorPolicy() || field.isMandatory()) {
			builder.addStatement("this.$L.set($L)", field.fieldName(), field.fieldName());
		} else {
			builder.addStatement("this.$L = $L", field.fieldName(), field.fieldName());
		}
		return builder.addStatement("return this")
				.build();
	}

	private boolean isAnEnforcedConstructorPolicy() {
		return this.buildable.constructorPolicy().equals(ConstructorPolicy.ENFORCED) ||
				this.buildable.constructorPolicy().equals(ConstructorPolicy.ENFORCED_ALLOW_NULLS);
	}

	private boolean isEnforcedConstructorPolicy() {
		return this.buildable.constructorPolicy().equals(ConstructorPolicy.ENFORCED);
	}

	private boolean isEnforcedAllowNullsConstructorPolicy() {
		return this.buildable.constructorPolicy().equals(ConstructorPolicy.ENFORCED_ALLOW_NULLS);
	}

	private List<FieldSpec> generateFields() {
		return buildableFields.stream()
				.map(this::generateField)
				.toList();
	}

	protected FieldSpec generateField(BuildableField field) {
		if (field.isConstructorArgument() && isAnEnforcedConstructorPolicy() || field.isMandatory()) {
			String methodName = this.buildable.constructorPolicy().equals(ConstructorPolicy.ENFORCED)
					? "notNullableOfNameWithinType"
					: "nullableOfNameWithinType";
			return FieldSpec
					.builder(ParameterizedTypeName.get(ClassName.get(RequiredField.class),
							TypeName.get(boxedType(field.type()))), field.fieldName(), Modifier.PRIVATE,
							Modifier.FINAL)
					.initializer("$T.$L(\"" + field.fieldName() + "\", \""
							+ this.typeDefinition.typeName() + "\")", RequiredField.class, methodName)
					.build();
		} else {
			return FieldSpec.builder(TypeName.get(field.type()), field.fieldName(), Modifier.PRIVATE)
					.build();
		}
	}

	protected TypeMirror boxedType(TypeMirror type) {
		if (type.getKind().isPrimitive()) {
			return typeUtils.boxedClass((PrimitiveType) type).asType();
		}
		return type;
	}

	private MethodSpec generateBuildMethod() {
		Builder builder = MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PUBLIC)
				.returns(className(this.typeDefinition));
		if (typeDefinition.containsSetterMethods()) {
			createConstructorAndSetterAwareBuildMethod(builder);
		} else {
			builder.addCode(CodeBlock.builder()
					.addStatement(CodeBlock.builder()
							.add("return ")
							.add(generateTypeInstantiationStatement())
							.build())
					.build());
		}
		return builder.build();
	}

	protected CodeBlock generateTypeInstantiationStatement() {
		return CodeBlock.builder()
				.add("new $T($L)", this.className(this.typeDefinition),
						this.toConstructorCallingStatement(this.constructorDefinition))
				.build();
	}

	protected String toConstructorCallingStatement(ConstructorDefinition constructorDefinition) {
		return constructorDefinition.parameters().stream()
				.map(param -> this.buildableFields.stream().anyMatch(f -> Objects.equals(f.fieldName(), param.name()))
						? String.format("%s%s", param.name(),
								isAnEnforcedConstructorPolicy() ? ".orElseThrow()"
										: "")
						: defaultForType(param.type()))
				.collect(Collectors.joining(", "));
	}

	private void createConstructorAndSetterAwareBuildMethod(Builder builder) {
		builder.addCode(CodeBlock.builder()
				.addStatement(CodeBlock.builder()
						.add("var instance = ")
						.add(generateTypeInstantiationStatement())
						.build())
				.build());
		this.buildableFields.stream()
				.filter(field -> !field.isConstructorArgument() && field.setterMethodName().isPresent())
				.forEach(field -> builder.addCode(generateFieldAssignment(field)));
		builder.addStatement("return instance");
	}

	protected CodeBlock generateFieldAssignment(BuildableField field) {
		if (field.isConstructorArgument() && isAnEnforcedConstructorPolicy() || field.isMandatory()) {
			return CodeBlock.builder()
					.addStatement("instance.$L(this.$L.orElseThrow())",
							setterName(field.setterMethodName().orElseThrow()), field.fieldName())
					.build();
		} else {
			return CodeBlock.builder()
					.addStatement("instance.%s(this.%s)".formatted(setterName(field.setterMethodName().orElseThrow()),
							field.fieldName()))
					.build();
		}
	}

	protected String defaultForType(TypeMirror type) {
		return switch (type.toString()) {
			case "int" -> "0";
			case "long" -> "0L";
			case "float" -> "0.0f";
			case "double" -> "0.0d";
			case "boolean" -> "false";
			default -> "null";
		};
	}

	private MethodSpec of() {
		CodeBlock.Builder body = CodeBlock.builder();
		body.addStatement("return new $L<>()", builderTypeName(this.typeDefinition));
		Builder builder = MethodSpec.methodBuilder("of")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addTypeVariables(builderTypeGenerics())
				.returns(builderType());
		for (ParameterDefinition parameter : this.typeDefinition.genericParameters()) {
			builder.addParameter(ParameterizedTypeName.get(ClassName.get("java.lang", "Class"),
					TypeVariableName.get(parameter.name())), String.format("%stype", parameter.name()));
		}
		builder.addCode(body.build());
		return builder.build();
	}

	protected TypeName builderType() {
		if (this.typeDefinition.genericParameters().isEmpty()) {
			String result;
			if (!this.buildable.packageName().isEmpty()) {
				result = this.buildable.packageName();
			} else {
				result = String.format("%s.builder", this.typeDefinition.packageName());
			}
			return ClassName.get(result, builderTypeName(this.typeDefinition));
		}
		List<TypeVariableName> typeVariableNames = toTypeVariableNames(this.typeDefinition);
		String result;
		if (!this.buildable.packageName().isEmpty()) {
			result = this.buildable.packageName();
		} else {
			result = String.format("%s.builder", this.typeDefinition.packageName());
		}
		return ParameterizedTypeName.get(ClassName.get(result, builderTypeName(this.typeDefinition)),
				typeVariableNames.toArray(new TypeName[0]));
	}

	private List<TypeVariableName> builderTypeGenerics() {
		List<TypeVariableName> typeVariableNames = new ArrayList<>();
		for (GenericParameterDefinition param : this.typeDefinition.genericParameters()) {
			List<TypeVariableName> bounds = new ArrayList<>();
			for (SimpleTypeDefinition definition : param.bounds()) {
				bounds.add(TypeVariableName.get(definition.typeName()));
			}
			typeVariableNames.add(TypeVariableName.get(param.name(), bounds.toArray(new TypeName[0])));
		}
		return typeVariableNames;
	}

	protected TypeName className(TypeDefinition definition) {
		if (definition.genericParameters().isEmpty()) {
			if (definition.isNested()) {
				return ClassName.get(definition.packageName(), definition.nestedIn())
						.nestedClass(definition.typeName());
			} else {
				return ClassName.get(definition.packageName(), definition.fullTypeName());
			}
		} else {
			List<TypeVariableName> genericParameters = toTypeVariableNames(definition);
			return ParameterizedTypeName.get(ClassName.get(definition.packageName(), definition.fullTypeName()),
					genericParameters.toArray(new TypeName[0]));
		}
	}

	private List<TypeVariableName> toTypeVariableNames(TypeDefinition definition) {
		List<TypeVariableName> genericParameters = new ArrayList<>();
		for (GenericParameterDefinition parameterDefinition : definition.genericParameters()) {
			genericParameters
					.add(TypeVariableName.get(parameterDefinition.name(), simpleClassNames(parameterDefinition.bounds())
							.toArray(new TypeName[parameterDefinition.bounds().size()])));
		}
		return genericParameters;
	}

	private List<TypeName> simpleClassNames(List<SimpleTypeDefinition> definitions) {
		List<TypeName> typeNames = new ArrayList<>();
		for (SimpleTypeDefinition definition : definitions) {
			typeNames.add(ClassName.get(definition.packageName(), definition.fullTypeName()));
		}
		return typeNames;
	}

	protected String setterName(String name) {
		if (buildable.setterPrefix().isEmpty()) {
			return name;
		}
		return Formatter.format("$setterPrefix$name", buildable.setterPrefix(),
				name.substring(0, 1).toUpperCase() + name.substring(1));
	}

	private boolean notExcluded(BuildableField field) {
		return !Arrays.asList(buildable.excludeFields()).contains(field.fieldName());
	}

	private MethodSpec generateConstructor() {
		return MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.build();
	}
}
