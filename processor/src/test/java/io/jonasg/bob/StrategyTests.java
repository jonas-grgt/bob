package io.jonasg.bob;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StrategyTests {
	@Nested
	class PermissiveStrategy {

		@Test
		void customFactoryMethodName() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/CustomFactoryMethodName/CustomFactoryMethodName.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.CustomFactoryMethodNameBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Permissive/CustomFactoryMethodName/Expected_CustomFactoryMethodNameBuilder.java"))
					.executeTest();
		}

		@Test
		void failWhenMandatoryAnnotationIsUsedWithPermissiveStrategy() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy/FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationFails()
					.andThat()
					.compilerMessage()
					.ofKindError()
					.contains(
							"PERMISSIVE (default) strategy cannot be combined with Mandatory fields, consider STRICT or STEP_WISE or remove the mandatory fields")
					.executeTest();
		}

		@Test
		void withDefaultsAsInnerClass() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/WithDefaultsAsInnerClass/WithDefaultsAsInnerClass.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.WithDefaultsAsInnerClassBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Permissive/WithDefaultsAsInnerClass/Expected_WithDefaultsAsInnerClass.java"))
					.executeTest();
		}

		@Test
		@Disabled
		void withDefaultsNoneStaticFields() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/WithDefaultsNoneStaticFields/WithDefaultsNoneStaticFields.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationFails()
					.andThat()
					.compilerMessage()
					.ofKindError()
					.contains("Default field (year) declared without static modifier.")
					.executeTest();
		}

		@Test
		void withDefaultsAsTopLevelClasWithoutDefaultedClass() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/WithDefaultsAsTopLevelClasWithoutDefaultedClass/WithDefaultsAsTopLevelClasWithoutDefaultedClass.java",
							"/tests/Strategies/Permissive/WithDefaultsAsTopLevelClasWithoutDefaultedClass/DefaultsClass.java")
					.whenCompiled()
					.thenExpectThat()
					.exceptionIsThrown(IllegalStateException.class,
							ex -> Assertions.assertThat(ex.getMessage()).isEqualTo(
									"@Buildable.Defaults must be defined as inner class of a buildable type or have an explicit buildable type set (through value parameter)"))
					.executeTest();
		}

		@Test
		void withDefaultsAsInnerClassWithoutBuildableTopLevelClass() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/WithDefaultsAsInnerClassWithoutBuildableTopLevelClass/WithDefaultsAsInnerClassWithoutBuildableTopLevelClass.java",
							"/tests/Strategies/Permissive/WithDefaultsAsInnerClassWithoutBuildableTopLevelClass/DummyBuildable.java")
					.whenCompiled()
					.thenExpectThat()
					.exceptionIsThrown(IllegalStateException.class,
							ex -> Assertions.assertThat(ex.getMessage()).isEqualTo(
									"@Buildable.Defaults without explicit Buildable type set (through value parameter) "
											+
											"must be inner class of a @Buildable class"))
					.executeTest();
		}

		@Test
		void withDefaultsAsTopLevelClass() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Permissive/WithDefaultsAsTopLevelClass/WithDefaultsAsTopLevelClass.java",
							"/tests/Strategies/Permissive/WithDefaultsAsTopLevelClass/DefaultsClass.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.WithDefaultsAsTopLevelClassBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Permissive/WithDefaultsAsTopLevelClass/Expected_WithDefaultsAsTopLevelClass.java"))
					.executeTest();
		}

	}

	@Nested
	class StrictStrategy {

		@Test
		void throwExceptionWhenMandatoryFieldsAreNotSet() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet/ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet/Expected_ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder.java"))
					.executeTest();
		}

		@Test
		void throwExceptionWhenMandatoryAnnotatedFieldsAreNotSet() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet/ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet/Expected_ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder.java"))
					.executeTest();
		}

		@Test
		void customFactoryMethodName() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/CustomFactoryMethodName/CustomFactoryMethodName.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.CustomFactoryMethodNameBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/CustomFactoryMethodName/Expected_CustomFactoryMethodNameBuilder.java"))
					.executeTest();
		}

		@Test
		void recordsAreBuildable() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/RecordsAreBuildable/RecordsAreBuildable.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.RecordsAreBuildableBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/RecordsAreBuildable/Expected_RecordsAreBuildableBuilder.java"))
					.executeTest();
		}

		@Test
		void optionalConstructorFieldIsNotEnforced() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/OptionalConstructorFieldInStrictStrategy/OptionalConstructorFieldInStrictStrategy.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.OptionalConstructorFieldInStrictStrategyBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/OptionalConstructorFieldInStrictStrategy/Expected_OptionalConstructorFieldInStrictStrategyBuilder.java"))
					.executeTest();
		}

		@Test
		void optionalConstructorParamAnnotationIsNotEnforced() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/OptionalConstructorParamInStrictStrategy/OptionalConstructorParamInStrictStrategy.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.OptionalConstructorParamInStrictStrategyBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/OptionalConstructorParamInStrictStrategy/Expected_OptionalConstructorParamInStrictStrategyBuilder.java"))
					.executeTest();
		}

		@Test
		void withDefaultsAsInnerClass() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/Strict/WithDefaultsAsInnerClass/WithDefaultsAsInnerClass.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.WithDefaultsAsInnerClassBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/Strict/WithDefaultsAsInnerClass/Expected_WithDefaultsAsInnerClass.java"))
					.executeTest();
		}

		@Nested
		class AllowNulls {

			@Test
			void constructorMandatoryFieldsCanBeSetToNullWithAllowNulls() {
				Cute.blackBoxTest()
						.given()
						.processors(List.of(BuildableProcessor.class))
						.andSourceFiles(
								"/tests/Strategies/Strict/AllowNulls/ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls/ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls.java")
						.whenCompiled()
						.thenExpectThat()
						.compilationSucceeds()
						.andThat()
						.generatedSourceFile(
								"io.jonasg.bob.test.ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder")
						.matches(
								CuteApi.ExpectedFileObjectMatcherKind.BINARY,
								JavaFileObjectUtils.readFromResource(
										"/tests/Strategies/Strict/AllowNulls/ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls/Expected_ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulsBuilder.java"))
						.executeTest();
			}

			@Test
			void annotatedMandatoryFieldsCanBeSetToNullWithAllowNulls() {
				Cute.blackBoxTest()
						.given()
						.processors(List.of(BuildableProcessor.class))
						.andSourceFiles(
								"/tests/Strategies/Strict/AllowNulls/AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls/AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls.java")
						.whenCompiled()
						.thenExpectThat()
						.compilationSucceeds()
						.andThat()
						.generatedSourceFile(
								"io.jonasg.bob.test.AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder")
						.matches(
								CuteApi.ExpectedFileObjectMatcherKind.BINARY,
								JavaFileObjectUtils.readFromResource(
										"/tests/Strategies/Strict/AllowNulls/AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls/Expected_AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder.java"))
						.executeTest();
			}

			@Test
			void fieldsDeclaredInBuildableAnnotationCanBeSetToNull() {
				Cute.blackBoxTest()
						.given()
						.processors(List.of(BuildableProcessor.class))
						.andSourceFiles(
								"/tests/Strategies/Strict/AllowNulls/FieldsDeclaredInBuildableAnnotationCanBeSetToNull/FieldsDeclaredInBuildableAnnotationCanBeSetToNull.java")
						.whenCompiled()
						.thenExpectThat()
						.compilationSucceeds()
						.andThat()
						.generatedSourceFile(
								"io.jonasg.bob.test.FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder")
						.matches(
								CuteApi.ExpectedFileObjectMatcherKind.BINARY,
								JavaFileObjectUtils.readFromResource(
										"/tests/Strategies/Strict/AllowNulls/FieldsDeclaredInBuildableAnnotationCanBeSetToNull/Expected_FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder.java"))
						.executeTest();
			}

			@Test
			void failWhenOptionalAnnotationIsUsedWithAllowNullsStrategy() {
				Cute.blackBoxTest()
						.given()
						.processors(List.of(BuildableProcessor.class))
						.andSourceFiles(
								"/tests/Strategies/Strict/AllowNulls/FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy/FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy.java")
						.whenCompiled()
						.thenExpectThat()
						.compilationFails()
						.andThat()
						.compilerMessage()
						.ofKindError()
						.contains(
								"ALLOW_NULLS strategy cannot be combined with optional fields, consider removing the optional annotation or remove the ALLOW_NULLS strategy")
						.executeTest();
			}

			@Test
			void failWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy() {
				Cute.blackBoxTest()
						.given()
						.processors(List.of(BuildableProcessor.class))
						.andSourceFiles(
								"/tests/Strategies/Strict/AllowNulls/FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy/FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy.java")
						.whenCompiled()
						.thenExpectThat()
						.compilationFails()
						.andThat()
						.compilerMessage()
						.ofKindError()
						.contains(
								"ALLOW_NULLS strategy cannot be combined with optional fields, consider removing the optional annotation or remove the ALLOW_NULLS strategy")
						.executeTest();
			}

		}
	}

	@Nested
	class StepWiseStrategy {

		@Test
		void customFactoryMethodName() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/CustomFactoryMethodName/CustomFactoryMethodName.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultCustomFactoryMethodNameBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/CustomFactoryMethodName/Expected_DefaultCustomFactoryMethodNameBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.CustomFactoryMethodNameBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/CustomFactoryMethodName/Expected_CustomFactoryMethodNameBuilder.java"))
					.executeTest();
		}

		@Test
		void generateStepBuilderWhenConstructorPolicyIsEnforcedStepWise() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise/GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise/Expected_DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise/Expected_GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder.java"))
					.executeTest();
		}

		@Test
		void generateStepBuilderWithSingleArgumentConstructor() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleArgumentConstructor/GenerateStepBuilderWithSingleArgumentConstructor.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleArgumentConstructor/Expected_DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.GenerateStepBuilderWithSingleArgumentConstructorBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleArgumentConstructor/Expected_GenerateStepBuilderWithSingleArgumentConstructorBuilder.java"))
					.executeTest();
		}

		@Test
		void generateStepBuilderWithSingleMandatoryAnnotatedField() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleMandatoryAnnotatedField/GenerateStepBuilderWithSingleMandatoryAnnotatedField.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultGenerateStepBuilderWithSingleMandatoryAnnotatedFieldBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleMandatoryAnnotatedField/Expected_DefaultGenerateStepBuilderWithSingleMandatoryAnnotatedFieldBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.GenerateStepBuilderWithSingleMandatoryAnnotatedFieldBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/GenerateStepBuilderWithSingleMandatoryAnnotatedField/Expected_GenerateStepBuilderWithSingleMandatoryAnnotatedFieldBuilder.java"))
					.executeTest();
		}

		@Test
		void optionalFieldInStepWise() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/OptionalFieldInStepWise/OptionalFieldInStepWise.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.OptionalFieldInStepWiseBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/OptionalFieldInStepWise/Expected_OptionalFieldInStepWise.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultOptionalFieldInStepWiseBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/OptionalFieldInStepWise/Expected_DefaultOptionalFieldInStepWiseBuilder.java"))

					.executeTest();
		}

		@Test
		void setterWithCustomPrefix() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"/tests/Strategies/StepWise/SetterWithCustomPrefix/SetterWithCustomPrefix.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.SetterWithCustomPrefixBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/SetterWithCustomPrefix/Expected_SetterWithCustomPrefixBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultSetterWithCustomPrefixBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"/tests/Strategies/StepWise/SetterWithCustomPrefix/Expected_DefaultSetterWithCustomPrefixBuilder.java"))
					.executeTest();
		}

		@Test
		void recordsAreBuildable() {
			Cute.blackBoxTest()
					.given()
					.processors(List.of(BuildableProcessor.class))
					.andSourceFiles(
							"tests/Strategies/StepWise/RecordsAreBuildable/RecordsAreBuildable.java")
					.whenCompiled()
					.thenExpectThat()
					.compilationSucceeds()
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.RecordsAreBuildableBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"tests/Strategies/StepWise/RecordsAreBuildable/Expected_RecordsAreBuildableBuilder.java"))
					.andThat()
					.generatedSourceFile(
							"io.jonasg.bob.test.DefaultRecordsAreBuildableBuilder")
					.matches(
							CuteApi.ExpectedFileObjectMatcherKind.BINARY,
							JavaFileObjectUtils.readFromResource(
									"tests/Strategies/StepWise/RecordsAreBuildable/Expected_DefaultRecordsAreBuildableBuilder.java"))
					.executeTest();
		}
	}
}
