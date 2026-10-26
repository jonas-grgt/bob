package io.jonasg.bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;

/**
 * @Buildable.Defaults must resolve for generic types and duplicates must be a
 *                     compile error rather than a processor crash (issue #115).
 */
class DefaultsResolutionTests {

	@ParameterizedTest(name = "{0}")
	@CsvSource(delimiter = '|', value = {
			"GenericType    | GenericCar",
			"NonGenericType | PlainCar",
	})
	void defaultsAreAppliedToBuildableType(String scenario, String type) {
		String dir = "/tests/DefaultsResolution/" + scenario + "/";
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(dir + type + ".java", dir + type + "Defaults.java", dir + type + "Usage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClassesTestedSuccessfullyBy(classLoader -> {
					Class<?> usage = classLoader.getClass("io.jonasg.bob.test." + type + "Usage");
					assertEquals("box/1985/volvo", usage.getMethod("run").invoke(null));
				})
				.executeTest();
	}

	@Test
	void twoDefaultsClassesForSameTypeIsACompileError() {
		String dir = "/tests/DefaultsResolution/DuplicateDefaults/";
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(dir + "DuplicatedCar.java",
						dir + "FirstDuplicatedCarDefaults.java",
						dir + "SecondDuplicatedCarDefaults.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationFails()
				.andThat()
				.compilerMessage()
				.ofKindError()
				.contains("DuplicatedCar")
				.executeTest()
				.executeCustomAssertions(outcome -> {
					List<String> errors = outcome.getCompilerMessages()
							.stream()
							.filter(m -> m.getKind() == Diagnostic.Kind.ERROR)
							.map(CuteApi.CompilerMessage::getMessage)
							.collect(Collectors.toList());
					assertFalse(errors.stream().anyMatch(e -> e.contains("uncaught exception")
							|| e.contains("Exception:") || e.contains("Duplicate key")),
							"processor crashed instead of reporting an error: " + errors);
					assertTrue(errors.stream().anyMatch(e -> e.contains("DuplicatedCar")
							&& e.contains("FirstDuplicatedCarDefaults")
							&& e.contains("SecondDuplicatedCarDefaults")),
							"error should name the type and both defaults classes: " + errors);
				});
	}
}
