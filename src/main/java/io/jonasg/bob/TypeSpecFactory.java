package io.jonasg.bob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
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

public class TypeSpecFactory {

	private final ConstructorDefinition constructorDefinition;

	private final TypeDefinition typeDefinition;

	private final Buildable buildable;

	private final List<ParameterDefinition> eligibleConstructorParams;

	private String builderTypeName(TypeDefinition source) {
		return Formatter.format("$typeName$suffix", source.typeName(), "Builder");
	}

	private TypeSpecFactory(TypeDefinition typeDefinition, Buildable buildable) {
		this.typeDefinition = typeDefinition;
		this.buildable = buildable;

		var buildableConstructors = typeDefinition.constructors().stream()
				.filter(c -> c.isAnnotatedWith(BuildableConstructor.class))
				.toList();
		if (buildableConstructors.size() > 1) {
			throw new IllegalArgumentException("Only one constructor can be annotated with @BuildableConstructor");
		}
		if (buildableConstructors.isEmpty()) {
			this.constructorDefinition = typeDefinition.constructors().stream()
					.max(Comparator.comparingInt(c -> c.parameters().size())).orElseThrow();
		} else {
			this.constructorDefinition = buildableConstructors.get(0);
		}

		var fieldNames = typeDefinition.fields().stream()
				.map(FieldDefinition::name)
				.toList();
		this.eligibleConstructorParams = this.constructorDefinition.parameters()
				.stream()
				.filter(p -> fieldNames.contains(p.name()))
				.toList();
	}

	public static TypeSpec produce(TypeDefinition typeDefinition, Buildable buildable) {
		return new TypeSpecFactory(typeDefinition, buildable).typeSpec();
	}

	private TypeSpec typeSpec() {
		TypeSpec.Builder builder = TypeSpec.classBuilder(builderTypeName(this.typeDefinition))
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		if (!this.typeDefinition.genericParameters().isEmpty())
			builder.addTypeVariables(toTypeVariableNames(this.typeDefinition));
		builder.addMethods(setters());
		builder.addFields(fields());
		builder.addMethod(buildMethod());
		builder.addMethod(constructor());
		if (!this.typeDefinition.genericParameters().isEmpty())
			builder.addMethod(of());
		return builder.build();
	}

	private List<MethodSpec> setters() {
		List<MethodSpec> setters = new ArrayList<>();
		for (ParameterDefinition field : this.eligibleConstructorParams) {
			if (notExcluded(field)) {
				MethodSpec.Builder setter = MethodSpec.methodBuilder(fieldName(field.name()))
						.addModifiers(Modifier.PUBLIC)
						.returns(builderType())
						.addParameter(TypeName.get(field.type()), field.name());
				setter
						.addStatement("this.$L = $L", field.name(), field.name())
						.build();
				setters.add(setter
						.addStatement("return this")
						.build());
			}
		}
		return setters;
	}

	private List<FieldSpec> fields() {
		return this.eligibleConstructorParams.stream()
				.map(field -> FieldSpec.builder(TypeName.get(field.type()), field.name(), Modifier.PRIVATE)
						.build())
				.toList();
	}

	private MethodSpec buildMethod() {
		MethodSpec.Builder builder = MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PUBLIC)
				.returns(className(this.typeDefinition));
		builder.addStatement("return new $T($L)", className(this.typeDefinition),
				this.constructorDefinition.parameters().stream()
						.map(param -> this.eligibleConstructorParams.contains(param) ? param.name()
								: defaultForType(param.type()))
						.collect(Collectors.joining(", ")));
		return builder.build();
	}

	private String defaultForType(TypeMirror type) {
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
		MethodSpec.Builder of = MethodSpec.methodBuilder("of")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addTypeVariables(builderTypeGenerics())
				.returns(builderType());
		for (ParameterDefinition parameter : this.typeDefinition.genericParameters())
			of.addParameter(ParameterizedTypeName.get(ClassName.get("java.lang", "Class"),
					TypeVariableName.get(parameter.name())), String.format("%stype", parameter.name()));
		of.addCode(body.build());
		return of.build();
	}

	private TypeName builderType() {
		if (this.typeDefinition.genericParameters().isEmpty()) {
			String result;
			if (!this.buildable.packageName().isEmpty())
				result = this.buildable.packageName();
			else
				result = String.format("%s.builder", this.typeDefinition.packageName());
			return ClassName.get(result, builderTypeName(this.typeDefinition));
		}
		List<TypeVariableName> typeVariableNames = toTypeVariableNames(this.typeDefinition);
		String result;
		if (!this.buildable.packageName().isEmpty())
			result = this.buildable.packageName();
		else
			result = String.format("%s.builder", this.typeDefinition.packageName());
		return ParameterizedTypeName.get(ClassName.get(result, builderTypeName(this.typeDefinition)),
				typeVariableNames.toArray(new TypeName[typeVariableNames.size()]));
	}

	private List<TypeVariableName> builderTypeGenerics() {
		List<TypeVariableName> typeVariableNames = new ArrayList<>();
		for (GenericParameterDefinition param : this.typeDefinition.genericParameters()) {
			List<TypeVariableName> bounds = new ArrayList<>();
			for (SimpleTypeDefinition definition : param.bounds()) {
				bounds.add(TypeVariableName.get(definition.typeName()));
			}
			typeVariableNames.add(TypeVariableName.get(param.name(), bounds.toArray(new TypeName[bounds.size()])));
		}
		return typeVariableNames;
	}

	private TypeName className(TypeDefinition definition) {
		if (definition.genericParameters().isEmpty()) {
			if (definition.isNested())
				return ClassName.get(definition.packageName(), definition.nestedIn())
						.nestedClass(definition.typeName());
			else
				return ClassName.get(definition.packageName(), definition.fullTypeName());
		} else {
			List<TypeVariableName> genericParameters = toTypeVariableNames(definition);
			return ParameterizedTypeName.get(ClassName.get(definition.packageName(), definition.fullTypeName()),
					genericParameters.toArray(new TypeName[genericParameters.size()]));
		}
	}

	private List<TypeVariableName> toTypeVariableNames(TypeDefinition definition) {
		List<TypeVariableName> genericParameters = new ArrayList<>();
		for (GenericParameterDefinition parameterDefinition : definition.genericParameters())
			genericParameters
					.add(TypeVariableName.get(parameterDefinition.name(), simpleClassNames(parameterDefinition.bounds())
							.toArray(new TypeName[parameterDefinition.bounds().size()])));
		return genericParameters;
	}

	private List<TypeName> simpleClassNames(List<SimpleTypeDefinition> definitions) {
		List<TypeName> typeNames = new ArrayList<>();
		for (SimpleTypeDefinition definition : definitions)
			typeNames.add(ClassName.get(definition.packageName(), definition.fullTypeName()));
		return typeNames;
	}

	private String fieldName(String name) {
		if (buildable.prefix().isEmpty()) {
			return name;
		}
		return Formatter.format("$prefix$name", buildable.prefix(),
				name.substring(0, 1).toUpperCase() + name.substring(1));
	}

	private boolean notExcluded(ParameterDefinition field) {
		return !Arrays.asList(buildable.excludes()).contains(field.name());
	}

	private MethodSpec constructor() {
		return MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.build();
	}
}
