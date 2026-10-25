package io.jonasg.bob;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.MethodSpec.Builder;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeVariableName;
import io.jonasg.bob.StepBuilderInterfaceTypeSpecFactory.BuilderDetails;
import io.jonasg.bob.definitions.ConstructorDefinition;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.ParameterDefinition;
import io.jonasg.bob.definitions.TypeDefinition;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BuilderTypeSpecFactory {

	protected final ConstructorDefinition constructorDefinition;

	protected final TypeDefinition typeDefinition;

	protected final Buildable buildable;

	protected final List<BuildableField> buildableFields;

	private final Types typeUtils;

	private final String packageName;

	private final boolean isInNullMarkedScope;

	private DefaultValues defaultValues;

	protected BuilderTypeSpecFactory(TypeDefinition typeDefinition,
			Buildable buildable,
			Types typeUtils,
			String packageName,
			boolean isInNullMarkedScope) {
		this.typeDefinition = typeDefinition;
		this.buildable = buildable;
		this.packageName = packageName;
		this.constructorDefinition = extractConstructorDefinitionFrom(typeDefinition);
		this.isInNullMarkedScope = isInNullMarkedScope;
		this.buildableFields = extractBuildableFieldsFrom(typeDefinition);
		this.typeUtils = typeUtils;
	}

	public List<TypeSpec> typeSpecs() {
		List<String> errors = collectInvalidStrategyCombinations();
		errors.addAll(collectConflictingMandatoryAndDefaults());
		errors.addAll(collectUnknownMandatoryFields());
		errors.addAll(collectExcludedMandatoryFields());
		List<String> warnings = collectRedundantMandatoryAnnotations();
		if (!errors.isEmpty() || !warnings.isEmpty()) {
			throw new BuilderValidationException(errors, warnings);
		}

		List<TypeSpec> typeSpecs = new ArrayList<>();

		String builderName = builderTypeName(this.typeDefinition);
		TypeSpec.Builder builder = TypeSpec.classBuilder(builderName)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL);

		if (this.strategy().contains(Strategy.STEP_WISE)) {
			var factory = new StepBuilderInterfaceTypeSpecFactory(this.typeDefinition, this.buildable,
					this.buildableFields, this.packageName);
			BuilderDetails builderDetails = factory.typeSpec(builderName);
			typeSpecs.add(builderDetails.typeSpec());
			builderDetails.interfaces().forEach(builder::addSuperinterface);
		}

		builder.addTypeVariables(this.typeDefinition.typeVariables());

		builder.addMethods(generateSetters());
		builder.addFields(generateFields());
		builder.addMethod(generateBuildMethod());
		builder.addMethod(generateConstructor());

		if (!this.typeDefinition.typeVariables().isEmpty()) {
			builder.addMethod(of());
		}

		if (!this.buildable.factoryName().isEmpty() && !this.strategy().contains(Strategy.STEP_WISE)) {
			builder.addMethod(addStaticFactoryMethod(this.buildable.factoryName()));
		}

		typeSpecs.add(builder.build());

		return typeSpecs;
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
				.map(p -> BuildableField.fromConstructor(p.name(), isMandatory(p), isOptional(p),
						isNullableField(p),
						p.type()));

		Stream<BuildableField> setterBuildableFields = this.typeDefinition.getSetterMethods(this.packageName)
				.stream()
				.filter(setter -> !eligibleConstructorParams
						.contains(new ParameterDefinition(setter.type(), setter.field().name())))
				.map(p -> {
					boolean fieldIsMandatory = Arrays.stream(this.buildable.mandatoryFields())
							.anyMatch(f -> Objects.equals(f, p.field().name()))
							|| p.field().isAnnotatedWith(Buildable.Mandatory.class);
					return BuildableField.fromSetter(p.field().name(), fieldIsMandatory,
							isNullableField(p.field()),
							p.methodName(), p.type());
				});
		return Stream.concat(constructorBuildableFields, setterBuildableFields)
				.collect(Collectors.toList());
	}

	private List<String> collectInvalidStrategyCombinations() {
		List<Strategy> strategies = this.strategy();
		List<String> errors = new ArrayList<>();
		if (strategies.contains(Strategy.ALLOW_NULLS)
				&& !strategies.contains(Strategy.STRICT)
				&& !strategies.contains(Strategy.STEP_WISE)) {
			errors.add("ALLOW_NULLS strategy requires STRICT or STEP_WISE to be combined with");
		}
		if (strategies.contains(Strategy.PERMISSIVE)) {
			if (strategies.contains(Strategy.STRICT)) {
				errors.add("Incompatible strategy combination: PERMISSIVE and STRICT cannot be combined");
			}
			if (strategies.contains(Strategy.STEP_WISE)) {
				errors.add("Incompatible strategy combination: PERMISSIVE and STEP_WISE cannot be combined");
			}
			if (strategies.contains(Strategy.ALLOW_NULLS)) {
				errors.add("Incompatible strategy combination: PERMISSIVE and ALLOW_NULLS cannot be combined");
			}
		}
		return errors;
	}

	private List<String> collectConflictingMandatoryAndDefaults() {
		List<String> errors = new ArrayList<>();
		if (this.defaultValues == null) {
			return errors;
		}
		Arrays.stream(this.buildable.mandatoryFields())
				.filter(mandatoryField -> this.defaultValues.forField(mandatoryField).isPresent())
				.forEach(mandatoryField -> errors.add(
						"Field '%s' is listed in mandatoryFields but also has a default value set via @Buildable.Defaults. Remove the field from mandatoryFields or remove its default value."
								.formatted(mandatoryField)));
		for (FieldDefinition field : this.typeDefinition.fields()) {
			if (field.isAnnotatedWith(Buildable.Mandatory.class)
					&& this.defaultValues.forField(field.name()).isPresent()) {
				errors.add(
						"Field '%s' is annotated with @Mandatory but also has a default value set via @Buildable.Defaults. Remove @Mandatory or the default value."
								.formatted(field.name()));
			}
		}
		for (ParameterDefinition param : this.constructorDefinition.parameters()) {
			if (param.isAnnotatedWith(Buildable.Mandatory.class)
					&& this.defaultValues.forField(param.name()).isPresent()) {
				errors.add(
						"Parameter '%s' is annotated with @Mandatory but also has a default value set via @Buildable.Defaults. Remove @Mandatory or the default value."
								.formatted(param.name()));
			}
		}
		return errors;
	}

	private List<String> collectRedundantMandatoryAnnotations() {
		if (!this.strategy().contains(Strategy.STRICT)) {
			return List.of();
		}
		List<String> warnings = new ArrayList<>();
		for (ParameterDefinition param : this.constructorDefinition.parameters()) {
			boolean fieldHasMandatory = this.typeDefinition.fields().stream()
					.filter(f -> f.name().equals(param.name()))
					.anyMatch(f -> f.isAnnotatedWith(Buildable.Mandatory.class));
			boolean paramHasMandatory = param.isAnnotatedWith(Buildable.Mandatory.class);
			if (fieldHasMandatory || paramHasMandatory) {
				warnings.add(
						"@Mandatory is redundant for constructor parameter '%s' — all constructor parameters are mandatory in STRICT strategy"
								.formatted(param.name()));
			}
		}
		return warnings;
	}

	private List<String> collectUnknownMandatoryFields() {
		List<String> errors = new ArrayList<>();
		List<String> knownFields = this.typeDefinition.fields().stream()
				.map(FieldDefinition::name)
				.toList();
		Arrays.stream(this.buildable.mandatoryFields())
				.filter(f -> !knownFields.contains(f))
				.forEach(f -> errors.add(
						"mandatoryFields contains '%s' which is not a declared field of %s"
								.formatted(f, this.typeDefinition.typeName())));
		return errors;
	}

	private List<String> collectExcludedMandatoryFields() {
		List<String> excluded = Arrays.asList(this.buildable.excludeFields());
		return Arrays.stream(this.buildable.mandatoryFields())
				.filter(excluded::contains)
				.map(f -> "Field '%s' is listed in both mandatoryFields and excludeFields. Remove it from one of them."
						.formatted(f))
				.toList();
	}

	private boolean isNullableField(ParameterDefinition p) {
		boolean paramHasNullable = this.constructorDefinition.parameters().stream()
				.filter(param -> param.name().equals(p.name()))
				.anyMatch(param -> param.hasAnnotation("org.jspecify.annotations.Nullable"));
		if (paramHasNullable)
			return true;
		boolean fieldHasNullable = this.typeDefinition.fields().stream()
				.filter(field -> field.name().equals(p.name()))
				.anyMatch(field -> field.hasAnnotation("org.jspecify.annotations.Nullable"));
		if (fieldHasNullable)
			return true;
		boolean fieldHasNonNull = this.typeDefinition.fields().stream()
				.filter(field -> field.name().equals(p.name()))
				.anyMatch(field -> field.hasAnnotation("org.jspecify.annotations.NonNull"));
		if (fieldHasNonNull)
			return false;
		return this.isInNullMarkedScope ? false : this.strategy().contains(Strategy.ALLOW_NULLS);
	}

	private boolean isNullableField(FieldDefinition field) {
		if (field.hasAnnotation("org.jspecify.annotations.Nullable"))
			return true;
		if (field.hasAnnotation("org.jspecify.annotations.NonNull"))
			return false;
		return !this.isInNullMarkedScope && this.strategy().contains(Strategy.ALLOW_NULLS);
	}

	private boolean isOptional(ParameterDefinition p) {
		return isFieldAnnotatedOptional(p) || isConstructorParamAnnotatedOptional(p);
	}

	private boolean isFieldAnnotatedOptional(ParameterDefinition p) {
		return typeDefinition.fields().stream()
				.filter(field -> field.name().equals(p.name()))
				.anyMatch(field -> field.isAnnotatedWith(Buildable.Optional.class));
	}

	private boolean isConstructorParamAnnotatedOptional(ParameterDefinition p) {
		return this.constructorDefinition.parameters().stream()
				.filter(param -> param.name().equals(p.name()))
				.anyMatch(param -> param.isAnnotatedWith(Buildable.Optional.class));
	}

	private boolean isMandatory(ParameterDefinition p) {
		return this.typeDefinition.fields().stream()
				.filter(field -> field.name().equals(p.name()))
				.anyMatch(field -> field.isAnnotatedWith(Buildable.Mandatory.class))
				|| this.constructorDefinition.parameters().stream()
						.filter(param -> param.name().equals(p.name()))
						.anyMatch(param -> param.isAnnotatedWith(Buildable.Mandatory.class));
	}

	private ConstructorDefinition extractConstructorDefinitionFrom(TypeDefinition typeDefinition) {
		var buildableConstructors = typeDefinition.constructors().stream()
				.filter(c -> c.isAnnotatedWith(Buildable.Constructor.class))
				.toList();
		if (buildableConstructors.size() > 1) {
			throw new IllegalArgumentException(
					"Only one constructor can be annotated with @Buildable.Constructor");
		}
		if (buildableConstructors.isEmpty()) {
			return typeDefinition.constructors().stream()
					.filter(c -> c.isAccessibleFrom(this.packageName))
					.max(Comparator.comparingInt(c -> c.parameters().size()))
					.orElseThrow(() -> new IllegalArgumentException(String.format(
							"%s has no constructor accessible from package '%s'",
							typeDefinition.typeName(), this.packageName)));
		}
		ConstructorDefinition annotated = buildableConstructors.get(0);
		if (!annotated.isAccessibleFrom(this.packageName)) {
			throw new IllegalArgumentException(String.format(
					"The @Buildable.Constructor of %s is not accessible from package '%s'",
					typeDefinition.typeName(), this.packageName));
		}
		return annotated;
	}

	private List<Strategy> strategy() {
		return Arrays.stream(this.buildable.strategy()).toList();
	}

	private List<MethodSpec> generateSetters() {
		return this.buildableFields.stream()
				.filter(this::notExcluded)
				.map(this::generateSetterForField)
				.toList();
	}

	protected MethodSpec generateSetterForField(BuildableField field) {

		var builder = MethodSpec.methodBuilder(setterName(field.name()))
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType())
				.addParameter(TypeName.get(field.type()), field.name());

		Optional<VariableElement> defaultField = getFieldDefault(field);

		if (isValidated(field) &&
				defaultField.isEmpty()) {
			if (strategy().contains(Strategy.STRICT) && field.isOptional()) {
				builder.addStatement("this.$L = $L", field.name(), field.name());
			} else {
				builder.addStatement("this.$L.set($L)", field.name(), field.name());
			}
		} else {
			if (getSupplierTypeArgument(field).isPresent()) {
				builder.addStatement("this.$L = () -> $L", field.name(), field.name());
			} else {
				builder.addStatement("this.$L = $L", field.name(), field.name());
			}
		}
		return builder.addStatement("return this")
				.build();
	}

	/**
	 * Whether the field must be set before build(); excluded fields never are.
	 */
	private boolean isValidated(BuildableField field) {
		return notExcluded(field)
				&& (field.isConstructorArgument() && isAnEnforcedConstructorPolicy() || field.isMandatory());
	}

	private boolean isAnEnforcedConstructorPolicy() {
		return this.strategy().contains(Strategy.STRICT) ||
				this.strategy().contains(Strategy.ALLOW_NULLS);
	}

	private List<FieldSpec> generateFields() {
		return buildableFields.stream()
				.map(this::generateField)
				.toList();
	}

	protected FieldSpec generateField(BuildableField field) {
		Optional<VariableElement> defaultField = getFieldDefault(field);
		if (isValidated(field)
				&& defaultField.isEmpty()) {

			if (strategy().contains(Strategy.STRICT) && field.isOptional()) {
				return FieldSpec.builder(TypeName.get(field.type()), field.name(), Modifier.PRIVATE)
						.build();
			} else {
				String methodName = field.isNullable()
						? "ofNullableField"
						: "ofNoneNullableField";
				return FieldSpec
						.builder(ParameterizedTypeName.get(ClassName.get(ValidatableField.class),
								TypeName.get(boxedType(field.type()))), field.name(), Modifier.PRIVATE,
								Modifier.FINAL)
						.initializer("$T.$L(\"" + field.name() + "\", \""
								+ this.typeDefinition.typeName() + "\")", ValidatableField.class,
								methodName)
						.build();
			}
		} else {
			return generateSimpleField(field);
		}
	}

	private FieldSpec generateSimpleField(BuildableField field) {
		if (this.defaultValues != null) {
			Optional<VariableElement> defaultField = getFieldDefault(field);
			if (defaultField.isPresent()) {
				if (!defaultField.get().getModifiers().contains(Modifier.STATIC)) {
					throw new IllegalStateException(
							"Default field (%s) declared without static modifier.".formatted(field.name()));
				}

				var defaultsPackageName = this.defaultValues.packageName();
				if (defaultsPackageName.equals(this.packageName)) {
					if (defaultField.get().getModifiers().contains(Modifier.PRIVATE)) {
						throw new IllegalStateException(
								"Default private field not accessible (%s) should be declared as public or package private."
										.formatted(field.name()));
					}
				} else {
					if (!defaultField.get().getModifiers().contains(Modifier.PUBLIC)) {
						throw new IllegalStateException(
								"Default field not accessible (%s) as not public and within same package as buildable type"
										.formatted(field.name()));
					}
				}

				TypeName fieldType = getSupplierTypeArgument(field)
						.map(arg -> (TypeName) ParameterizedTypeName.get(
								ClassName.get("java.util.function", "Supplier"), TypeName.get(arg)))
						.orElseGet(() -> TypeName.get(field.type()));
				return FieldSpec.builder(fieldType, field.name(), Modifier.PRIVATE)
						.initializer("$T.$L", TypeName.get(defaultValues.asType()), field.name())
						.build();
			}
		}

		return FieldSpec.builder(TypeName.get(field.type()), field.name(), Modifier.PRIVATE)
				.build();
	}

	private Optional<VariableElement> getFieldDefault(BuildableField field) {
		if (this.defaultValues == null) {
			return Optional.empty();
		}
		return this.defaultValues.forField(field.name());
	}

	private Optional<TypeMirror> getSupplierTypeArgument(BuildableField field) {
		if (this.defaultValues == null) {
			return Optional.empty();
		}
		return this.defaultValues.supplierTypeArgument(field.name());
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
		if (isAnEnforcedConstructorPolicy()
				|| (!strategy().contains(Strategy.STEP_WISE)
						&& buildableFields.stream().anyMatch(BuildableField::isMandatory))) {
			builder.addCode(generateValidationCode());
		}
		if (typeDefinition.containsSetterMethods(this.packageName)) {
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

	private CodeBlock generateValidationCode() {
		var validatableFields = buildableFields.stream()
				.filter(f -> isValidated(f)
						&& !f.isOptional())
				.toList();

		if (validatableFields.isEmpty()) {
			return CodeBlock.builder().build();
		}

		var builder = CodeBlock.builder();

		builder.addStatement("var missingFields = new java.util.ArrayList<String>()");
		validatableFields.stream()
				.filter(field -> getFieldDefault(field).isEmpty())
				.forEach(field -> {
					builder.addStatement("if (!$L.isValid()) missingFields.add(\"$L\")",
							field.name(), field.name());
				});

		builder.beginControlFlow("if (missingFields.size() == 1)");
		builder.addStatement("throw new $T(missingFields.get(0), \"$L\")",
				ClassName.get("io.jonasg.bob", "MandatoryFieldMissingException"),
				typeDefinition.typeName());
		builder.nextControlFlow("else if (!missingFields.isEmpty())");
		builder.addStatement("throw new $T(missingFields.stream()"
				+ ".map(f -> new $T(f, \"$L\"))"
				+ ".toList())",
				ClassName.get("io.jonasg.bob", "MandatoryFieldsMissingException"),
				ClassName.get("io.jonasg.bob", "MissingField"),
				typeDefinition.typeName());
		builder.endControlFlow();

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
				.map(param -> {
					var matchingField = this.buildableFields.stream()
							.filter(f -> Objects.equals(f.name(), param.name()))
							.findFirst();
					if (matchingField.isEmpty()) {
						return defaultForType(param.type());
					}
					boolean isOptionalInStrict = strategy().contains(Strategy.STRICT)
							&& matchingField.get().isOptional();
					boolean isSupplier = getSupplierTypeArgument(matchingField.get()).isPresent()
							|| (isValidated(matchingField.get())
									&& !isOptionalInStrict
									&& getFieldDefault(matchingField.get()).isEmpty());
					return String.format("%s%s", param.name(), isSupplier ? ".get()" : "");
				})
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
		if (isValidated(field)) {
			return CodeBlock.builder()
					.addStatement("instance.$L(this.$L.get())",
							field.setterMethodName().orElseThrow(), field.name())
					.build();
		} else {
			String suffix = getSupplierTypeArgument(field).isPresent() ? ".get()" : "";
			return CodeBlock.builder()
					.addStatement(
							String.format("instance.%s(this.%s" + suffix + ")",
									field.setterMethodName().orElseThrow(), field.name()))
					.build();
		}
	}

	protected String defaultForType(TypeMirror type) {
		// byte and short need a cast: constants are not narrowed in invocation contexts
		return switch (type.getKind()) {
			case BYTE -> "(byte) 0";
			case SHORT -> "(short) 0";
			case CHAR -> "'\\u0000'";
			case INT -> "0";
			case LONG -> "0L";
			case FLOAT -> "0.0f";
			case DOUBLE -> "0.0d";
			case BOOLEAN -> "false";
			default -> "null";
		};
	}

	private MethodSpec of() {
		CodeBlock.Builder body = CodeBlock.builder();
		body.addStatement("return new $L<>()", builderTypeName(this.typeDefinition));
		Builder builder = MethodSpec.methodBuilder("of")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addTypeVariables(this.typeDefinition.typeVariables())
				.returns(builderType());
		for (TypeVariableName typeVariable : this.typeDefinition.typeVariables()) {
			builder.addParameter(ParameterizedTypeName.get(ClassName.get(Class.class),
					TypeVariableName.get(typeVariable.name())), String.format("%stype", typeVariable.name()));
		}
		builder.addCode(body.build());
		return builder.build();
	}

	protected TypeName builderType() {
		return this.typeDefinition.withTypeVariables(
				ClassName.get(this.packageName, builderTypeName(this.typeDefinition)));
	}

	protected TypeName className(TypeDefinition definition) {
		return definition.withTypeVariables(definition.className());
	}

	private MethodSpec addStaticFactoryMethod(String name) {
		return MethodSpec.methodBuilder(name)
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addTypeVariables(this.typeDefinition.typeVariables())
				.returns(this.builderType())
				.addStatement("return new $T()", this.builderType())
				.build();
	}

	protected String setterName(String name) {
		if (buildable.setterPrefix().isEmpty()) {
			return name;
		}
		return Formatter.format("$setterPrefix$name", buildable.setterPrefix(),
				name.substring(0, 1).toUpperCase() + name.substring(1));
	}

	private boolean notExcluded(BuildableField field) {
		return !Arrays.asList(buildable.excludeFields()).contains(field.name());
	}

	private MethodSpec generateConstructor() {
		ClassName rawClassName = this.typeDefinition.className();
		var constructorBuilder = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC);
		if (!this.buildable.runtimeDefaults()) {
			return constructorBuilder.build();
		}
		constructorBuilder.beginControlFlow("try")
				.addStatement("Class<?> resolverClass = Class.forName($S)", "io.jonasg.bob.DefaultsResolver");
		if (this.buildable.setterPrefix().isEmpty()) {
			constructorBuilder
					.addStatement(
							"java.lang.reflect.Method method = resolverClass.getMethod($S, Object.class, Class.class)",
							"applyDefaults")
					.addStatement("method.invoke(null, this, $T.class)", rawClassName);
		} else {
			constructorBuilder
					.addStatement(
							"java.lang.reflect.Method method = resolverClass.getMethod($S, Object.class, Class.class, String.class)",
							"applyDefaults")
					.addStatement("method.invoke(null, this, $T.class, $S)", rawClassName,
							this.buildable.setterPrefix());
		}
		constructorBuilder.nextControlFlow("catch ($T ignored)", ReflectiveOperationException.class)
				.endControlFlow();
		return constructorBuilder.build();
	}

	private String builderTypeName(TypeDefinition source) {
		String name = Formatter.format("$typeName$suffix", source.typeName(), "Builder");
		if (this.strategy().contains(Strategy.STEP_WISE)) {
			name = "Default" + name;
		}
		return name;
	}

	public void setDefaults(DefaultValues defaultValues) {
		this.defaultValues = defaultValues;
	}
}
