package io.jonasg.bob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.toolisticon.cute.Cute;

/**
 * Constructor params without a matching field are passed their type's default;
 * byte, short and char must be covered too (issue #113).
 */
class DefaultsForUnmatchedParamsTests {

	@ParameterizedTest(name = "{0}")
	@ValueSource(strings = { "UnmatchedByteParam", "UnmatchedShortParam", "UnmatchedCharParam" })
	void unmatchedPrimitiveParamIsPassedItsDefault(String fixture) {
		String dir = "/tests/DefaultsForUnmatchedParams/";
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(dir + fixture + ".java", dir + fixture + "Usage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClassesTestedSuccessfullyBy(classLoader -> {
					Class<?> usage = classLoader.getClass("io.jonasg.bob.test." + fixture + "Usage");
					assertEquals("foo/true", usage.getMethod("run").invoke(null));
				})
				.executeTest();
	}
}
