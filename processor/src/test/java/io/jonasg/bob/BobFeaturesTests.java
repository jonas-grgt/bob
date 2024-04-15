package io.jonasg.bob;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;

public class BobFeaturesTests {

	@Test
	void failWhenMultipleConstructorAreAnnotatedWithBuildableConstructor() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/failing-compilation/MultipleBuildableConstructorAnnotationsPresent/MultipleBuildableConstructorAnnotationsPresent.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationFails()
				.andThat()
				.compilerMessage()
				.ofKindError()
				.contains("Only one constructor can be annotated with @Buildable.Constructor")
				.executeTest();
	}

	@Test
	void allConstructorParamsAreBuildableAndByDefaultNotEnforced() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/AllConstructorParamsAreBuildableAndByDefaultNotEnforced/AllConstructorParamsAreBuildableAndByDefaultNotEnforced.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/AllConstructorParamsAreBuildableAndByDefaultNotEnforced/Expected_AllConstructorParamsAreBuildableAndByDefaultNotEnforced.java"))
				.executeTest();
	}

	@Test
	void allConstructorParamsAreBuildableIfHavingMatchingField() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/AllConstructorParamsAreBuildableIfHavingMatchingField/AllConstructorParamsAreBuildableIfHavingMatchingField.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/AllConstructorParamsAreBuildableIfHavingMatchingField/Expected_AllConstructorParamsAreBuildableIfHavingMatchingField.java"))
				.executeTest();
	}

	@Test
	void defaultValuesForParamsWithNoneMatchingField() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/AllConstructorParamsAreBuildableIfHavingMatchingField/AllConstructorParamsAreBuildableIfHavingMatchingField.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/AllConstructorParamsAreBuildableIfHavingMatchingField/Expected_AllConstructorParamsAreBuildableIfHavingMatchingField.java"))
				.executeTest();
	}

	@Test
	void overrideDefaultPackage() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/successful-compilation/OverrideDefaultPackage/OverrideDefaultPackage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.other.OverrideDefaultPackageBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/OverrideDefaultPackage/Expected_OverrideDefaultPackage.java"))
				.executeTest();
	}

	@Test
	void setterWithCustomPrefix() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/successful-compilation/SetterWithCustomPrefix/SetterWithCustomPrefix.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.builder.SetterWithCustomPrefixBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/SetterWithCustomPrefix/Expected_SetterWithCustomPrefix.java"))
				.executeTest();
	}

	@Test
	void useConstructorAnnotatedWithBuildableConstructor() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/UseConstructorAnnotatedWithBuildableConstructor/UseConstructorAnnotatedWithBuildableConstructor.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.UseConstructorAnnotatedWithBuildableConstructorBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/UseConstructorAnnotatedWithBuildableConstructor/Expected_UseConstructorAnnotatedWithBuildableConstructor.java"))
				.executeTest();
	}

	@Test
	void useConstructorWithTheMostNumberOfParameters() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/UseConstructorWithTheMostNumberOfParameters/UseConstructorWithTheMostNumberOfParameters.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.UseConstructorWithTheMostNumberOfParametersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/UseConstructorWithTheMostNumberOfParameters/Expected_UseConstructorWithTheMostNumberOfParameters.java"))
				.executeTest();
	}

	@Test
	void useFirstConstructorWithTheMostNumberOfParameters() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/UseFirstConstructorWithTheMostNumberOfParameters/UseFirstConstructorWithTheMostNumberOfParameters.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.UseFirstConstructorWithTheMostNumberOfParametersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/UseFirstConstructorWithTheMostNumberOfParameters/Expected_UseFirstConstructorWithTheMostNumberOfParameters.java"))
				.executeTest();
	}

	@Test
	void genericsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/successful-compilation/GenericsAreBuildable/GenericsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.builder.GenericsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/GenericsAreBuildable/Expected_GenericsAreBuildable.java"))
				.executeTest();
	}

	@Test
	void recordsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/successful-compilation/RecordsAreBuildable/RecordsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.builder.RecordsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/RecordsAreBuildable/Expected_RecordsAreBuildable.java"))
				.executeTest();
	}

	@Test
	void allPublicSettersThatHaveCorrespondingFieldsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable/Expected_AllPublicSettersThatHaveCorrespondingFieldsAreBuildable.java"))
				.executeTest();
	}

	@Test
	void constructorParametersAreEnforcedWhenConstructorPolicyIsEnforced() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/successful-compilation/ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced/ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.builder.ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/successful-compilation/ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced/Expected_ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced.java"))
				.executeTest();
	}

}
