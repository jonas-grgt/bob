package io.jonasg.bob;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

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
		@EnabledForJreRange(min = JRE.JAVA_12, disabledReason = "Records do not exist yet")
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
		@EnabledForJreRange(min = JRE.JAVA_12, disabledReason = "Records do not exist yet")
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
