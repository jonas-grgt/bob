package io.jonasg.bob;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;

public class BobTests {

	@Test
	void failWhenMultipleConstructorsAreAnnotatedWithBuildableConstructor() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/MultipleBuildableConstructorsAnnotationsPresent/MultipleBuildableConstructorsAnnotationsPresent.java")
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
						"/tests/AllConstructorParamsAreBuildableAndByDefaultNotEnforced/AllConstructorParamsAreBuildableAndByDefaultNotEnforced.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/AllConstructorParamsAreBuildableAndByDefaultNotEnforced/Expected_AllConstructorParamsAreBuildableAndByDefaultNotEnforced.java"))
				.executeTest();
	}

	@Test
	void allConstructorParamsAreBuildableIfHavingMatchingField() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/AllConstructorParamsAreBuildableIfHavingMatchingField/AllConstructorParamsAreBuildableIfHavingMatchingField.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/AllConstructorParamsAreBuildableIfHavingMatchingField/Expected_AllConstructorParamsAreBuildableIfHavingMatchingField.java"))
				.executeTest();
	}

	@Test
	void booleanFieldHasOherNameThanSetter() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/BooleanFieldHasOtherNameThanSetter/BooleanFieldHasOtherNameThanSetter.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.BooleanFieldHasOtherNameThanSetterBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/BooleanFieldHasOtherNameThanSetter/Expected_BooleanFieldHasOtherNameThanSetter.java"))
				.executeTest();
	}

	@Test
	void defaultValuesForParamsWithNoneMatchingField() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/AllConstructorParamsAreBuildableIfHavingMatchingField/AllConstructorParamsAreBuildableIfHavingMatchingField.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/AllConstructorParamsAreBuildableIfHavingMatchingField/Expected_AllConstructorParamsAreBuildableIfHavingMatchingField.java"))
				.executeTest();
	}

	@Test
	void overrideDefaultPackage() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/OverrideDefaultPackage/OverrideDefaultPackage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.other.OverrideDefaultPackageBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/OverrideDefaultPackage/Expected_OverrideDefaultPackage.java"))
				.executeTest();
	}

	@Test
	void setterWithCustomPrefix() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/SetterWithCustomPrefix/SetterWithCustomPrefix.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.SetterWithCustomPrefixBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/SetterWithCustomPrefix/Expected_SetterWithCustomPrefix.java"))
				.executeTest();
	}

	@Test
	void useConstructorAnnotatedWithBuildableConstructor() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/UseConstructorAnnotatedWithBuildableConstructor/UseConstructorAnnotatedWithBuildableConstructor.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.UseConstructorAnnotatedWithBuildableConstructorBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/UseConstructorAnnotatedWithBuildableConstructor/Expected_UseConstructorAnnotatedWithBuildableConstructor.java"))
				.executeTest();
	}

	@Test
	void useConstructorWithTheMostNumberOfParameters() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/UseConstructorWithTheMostNumberOfParameters/UseConstructorWithTheMostNumberOfParameters.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.UseConstructorWithTheMostNumberOfParametersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/UseConstructorWithTheMostNumberOfParameters/Expected_UseConstructorWithTheMostNumberOfParameters.java"))
				.executeTest();
	}

	@Test
	void useFirstConstructorWithTheMostNumberOfParameters() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/UseFirstConstructorWithTheMostNumberOfParameters/UseFirstConstructorWithTheMostNumberOfParameters.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.UseFirstConstructorWithTheMostNumberOfParametersBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/UseFirstConstructorWithTheMostNumberOfParameters/Expected_UseFirstConstructorWithTheMostNumberOfParameters.java"))
				.executeTest();
	}

	@Test
	void genericsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/GenericsAreBuildable/GenericsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.GenericsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/GenericsAreBuildable/Expected_GenericsAreBuildable.java"))
				.executeTest();
	}

	@Test
	void allPublicSettersThatHaveCorrespondingFieldsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile(
						"io.jonasg.bob.test.AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/AllPublicSettersThatHaveCorrespondingFieldsAreBuildable/Expected_AllPublicSettersThatHaveCorrespondingFieldsAreBuildable.java"))
				.executeTest();
	}
}
