package io.jonasg.bob;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

class InheritanceTests {

	@Test
	void subclassConstructorParamsMatchingInheritedFieldsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/SubclassConstructorTakesInheritedFields/SubclassConstructorTakesInheritedFields.java",
						"/tests/Inheritance/SubclassConstructorTakesInheritedFields/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.SubclassConstructorTakesInheritedFieldsBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/SubclassConstructorTakesInheritedFields/Expected_SubclassConstructorTakesInheritedFields.java"))
				.executeTest();
	}

	@Test
	void inheritedSettersAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/InheritedSetters/InheritedSetters.java",
						"/tests/Inheritance/InheritedSetters/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.InheritedSettersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/InheritedSetters/Expected_InheritedSetters.java"))
				.executeTest();
	}

	@Test
	void protectedAndPackagePrivateInheritedSettersAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/AccessibleInheritedSetters/AccessibleInheritedSetters.java",
						"/tests/Inheritance/AccessibleInheritedSetters/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.AccessibleInheritedSettersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/AccessibleInheritedSetters/Expected_AccessibleInheritedSetters.java"))
				.executeTest();
	}

	@Test
	void privateInheritedSetterIsNotBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/PrivateInheritedSetter/PrivateInheritedSetter.java",
						"/tests/Inheritance/PrivateInheritedSetter/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PrivateInheritedSetterBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/PrivateInheritedSetter/Expected_PrivateInheritedSetter.java"))
				.executeTest();
	}

	@Test
	void fieldsFromMultipleInheritanceLevelsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/MultiLevelInheritance/MultiLevelInheritance.java",
						"/tests/Inheritance/MultiLevelInheritance/Parent.java",
						"/tests/Inheritance/MultiLevelInheritance/Grandparent.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.MultiLevelInheritanceBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/MultiLevelInheritance/Expected_MultiLevelInheritance.java"))
				.executeTest();
	}

	@Test
	void subclassFieldHidesInheritedField() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/FieldHiding/FieldHiding.java",
						"/tests/Inheritance/FieldHiding/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.FieldHidingBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/FieldHiding/Expected_FieldHiding.java"))
				.executeTest();
	}

	@Test
	void inheritedGenericFieldTypeIsResolved() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/GenericInheritedField/GenericInheritedField.java",
						"/tests/Inheritance/GenericInheritedField/Animal.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.GenericInheritedFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/GenericInheritedField/Expected_GenericInheritedField.java"))
				.executeTest();
	}

	@Test
	void inheritedFieldsAreBuildableInStrictStrategy() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/StrictInheritedFields/StrictInheritedFields.java",
						"/tests/Inheritance/StrictInheritedFields/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.StrictInheritedFieldsBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/StrictInheritedFields/Expected_StrictInheritedFields.java"))
				.executeTest();
	}

	@Test
	void inheritedFieldsAreBuildableInStepWiseStrategy() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/StepWiseInheritedFields/StepWiseInheritedFields.java",
						"/tests/Inheritance/StepWiseInheritedFields/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.StepWiseInheritedFieldsBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/StepWiseInheritedFields/Expected_StepWiseInheritedFieldsBuilder.java"))
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.DefaultStepWiseInheritedFieldsBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/StepWiseInheritedFields/Expected_DefaultStepWiseInheritedFieldsBuilder.java"))
				.executeTest();
	}

	@Test
	void mandatoryAnnotationOnInheritedFieldIsHonored() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/MandatoryInheritedField/MandatoryInheritedField.java",
						"/tests/Inheritance/MandatoryInheritedField/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.MandatoryInheritedFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/MandatoryInheritedField/Expected_MandatoryInheritedField.java"))
				.executeTest();
	}

	@Test
	void staticInheritedFieldIsExcluded() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Inheritance/StaticInheritedFieldExcluded/StaticInheritedFieldExcluded.java",
						"/tests/Inheritance/StaticInheritedFieldExcluded/Vehicle.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.StaticInheritedFieldExcludedBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/Inheritance/StaticInheritedFieldExcluded/Expected_StaticInheritedFieldExcluded.java"))
				.executeTest();
	}
}