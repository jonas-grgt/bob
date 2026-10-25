package io.jonasg.bob;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import io.jonasg.bob.TypeSpecInterfaceBuilder.InterfaceBuilder;
import io.jonasg.bob.definitions.TypeDefinition;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static io.jonasg.bob.Strategy.STEP_WISE;
import static io.jonasg.bob.Strategy.STRICT;

class StepBuilderInterfaceTypeSpecFactory {

	private final Buildable buildable;

	private final TypeDefinition typeDefinition;

	private final List<BuildableField> buildableFields;

	private final String packageName;

	public StepBuilderInterfaceTypeSpecFactory(TypeDefinition typeDefinition,
			Buildable buildable,
			List<BuildableField> buildableFields,
			String packageName) {
		this.buildable = buildable;
		this.typeDefinition = typeDefinition;
		this.buildableFields = buildableFields;
		this.packageName = packageName;
	}

	record BuilderDetails(TypeSpec typeSpec, Set<TypeName> interfaces) {
		@Override
		public String toString() {
			return "BuilderDetails[" +
				   "typeSpec=" + typeSpec + ", " +
				   "interfaces=" + interfaces + ']';
		}

	}

	BuilderDetails typeSpec(String builderImplName) {
		Set<TypeName> interfaces = new HashSet<>();
		String builderInterfaceName = String.format("%sBuilder", this.typeDefinition.typeName());
		Builder stepBuilderBuilder = TypeSpec.interfaceBuilder(builderInterfaceName)
				.addModifiers(Modifier.PUBLIC)
				.addTypeVariables(this.typeDefinition.typeVariables());
		interfaces.add(generic(ClassName.get(this.packageName, builderInterfaceName)));

		List<BuildableField> reversedBuildableFields = reverseList(this.buildableFields);

		// Determine mandatory fields (excluding optional ones)
		List<BuildableField> mandatoryFields = reversedBuildableFields
				.stream()
				.filter(this::notExcluded)
				.filter(field -> (field.isConstructorArgument() && isEnforcedConstructorPolicy())
						|| field.isMandatory())
				.filter(field -> !field.isOptional())
				.toList();

		// Generate BuildStep interface containing the build method plus all optional
		// fields.
		Set<BuildableField> mandatoryFieldSet = new HashSet<>(mandatoryFields);
		InterfaceBuilder buildStepInterfaceBuilder = TypeSpecInterfaceBuilder.anInterface("BuildStep")
				.typeVariables(this.typeDefinition.typeVariables());
		reversedBuildableFields.stream()
				.filter(this::notExcluded)
				.filter(field -> !mandatoryFieldSet.contains(field))
				.forEach(field -> buildStepInterfaceBuilder.addMethod(MethodSpec.methodBuilder(setterName(field.name()))
						.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
						.returns(generic(ClassName.get("", "BuildStep")))
						.addParameter(TypeName.get(field.type()), field.name())
						.build()));
		// Add terminal build method
		buildStepInterfaceBuilder.addMethod(MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.returns(generic(this.typeDefinition.className()))
				.build());
		buildStepInterfaceBuilder.build();
		TypeSpec buildStep = buildStepInterfaceBuilder.build();
		stepBuilderBuilder.addType(buildStep);
		interfaces.add(generic(ClassName.get("", builderInterfaceName + "." + "BuildStep")));

		// Without mandatory fields the builder starts directly at the BuildStep
		if (mandatoryFields.isEmpty()) {
			stepBuilderBuilder.addMethod(staticFactoryMethod(generic(ClassName.get("", "BuildStep")), builderImplName));
			return new BuilderDetails(stepBuilderBuilder.build(), interfaces);
		}
		stepBuilderBuilder.addMethod(
				staticFactoryMethod(generic(ClassName.get(this.packageName, builderInterfaceName)), builderImplName));

		// Add each mandatory field as a separate interface,
		// Skipping the last field because that should be defined as a method within
		// the main Builder interface itself.
		AtomicReference<TypeSpec> nextStep = new AtomicReference<>(buildStep);
		mandatoryFields
				.subList(0, mandatoryFields.size() - 1)
				.stream()
				.filter(this::notExcluded)
				.map(field -> {
					String name = String.format("%sStep", capitalize(field.name()));
					interfaces.add(generic(ClassName.get("", builderInterfaceName + "." + name)));
					return TypeSpecInterfaceBuilder.functionalInterface(name)
							.typeVariables(this.typeDefinition.typeVariables())
							.methodName(setterName(field.name()))
							.addArgument(TypeName.get(field.type()), field.name())
							.returns(generic(ClassName.get("", nextStep.get().name())))
							.build();
				})
				.peek(nextStep::set)
				.forEach(stepBuilderBuilder::addType);

		// The initial field to be built
		BuildableField buildableField = mandatoryFields
				.get(mandatoryFields.size() - 1);
		stepBuilderBuilder.addMethod(MethodSpec.methodBuilder(setterName(buildableField.name()))
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.addParameter(TypeName.get(buildableField.type()), buildableField.name())
				.returns(generic(ClassName.get("", nextStep.get().name())))
				.build());
		return new BuilderDetails(stepBuilderBuilder.build(), interfaces);
	}

	private MethodSpec staticFactoryMethod(TypeName returnType, String builderImplName) {
		return MethodSpec.methodBuilder(builderStaticFactoryMethodName())
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addTypeVariables(this.typeDefinition.typeVariables())
				.returns(returnType)
				.addStatement(this.typeDefinition.typeVariables().isEmpty()
						? "return new $L()"
						: "return new $L<>()", builderImplName)
				.build();
	}

	private TypeName generic(ClassName raw) {
		return this.typeDefinition.withTypeVariables(raw);
	}

	private String builderStaticFactoryMethodName() {
		return this.buildable.factoryName().isEmpty() ? "newBuilder" : this.buildable.factoryName();
	}

	private String capitalize(String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	private boolean isEnforcedConstructorPolicy() {
		return Arrays.stream(this.buildable.strategy())
				.anyMatch(s -> List.of(STRICT, STEP_WISE).contains(s));
	}

	private boolean notExcluded(BuildableField field) {
		return !Arrays.asList(buildable.excludeFields()).contains(field.name());
	}

	private <T> List<T> reverseList(List<T> originalList) {
		List<T> reversedList = new ArrayList<>();
		for (int i = originalList.size() - 1; i >= 0; i--) {
			reversedList.add(originalList.get(i));
		}
		return reversedList;
	}

	protected String setterName(String name) {
		if (buildable.setterPrefix().isEmpty()) {
			return name;
		}
		return Formatter.format("$setterPrefix$name", buildable.setterPrefix(),
				name.substring(0, 1).toUpperCase() + name.substring(1));
	}
}
