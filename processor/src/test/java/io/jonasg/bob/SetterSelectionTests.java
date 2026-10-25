package io.jonasg.bob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.toolisticon.cute.Cute;

/**
 * One setter per field must be picked, preferring the one whose parameter
 * matches the field type (issue #114).
 */
class SetterSelectionTests {

	private static void assertSingleBuilderMethod(Class<?> builder, String name, Class<?> paramType) {
		List<Method> methods = Arrays.stream(builder.getDeclaredMethods())
				.filter(m -> m.getName().equals(name))
				.toList();
		assertEquals(1, methods.size(), "builder methods named '" + name + "': " + methods);
		assertEquals(List.of(paramType), List.of(methods.get(0).getParameterTypes()),
				"parameter types of builder method '" + name + "'");
	}

	@ParameterizedTest(name = "{0}")
	@ValueSource(strings = { "FluentAndSetter", "OverloadedSetters" })
	void singleSetterMatchingFieldTypeIsUsed(String fixture) {
		String dir = "/tests/SetterSelection/";
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(dir + fixture + ".java", dir + fixture + "Usage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClassesTestedSuccessfullyBy(classLoader -> {
					Class<?> builder = classLoader.getClass("io.jonasg.bob.test." + fixture + "Builder");
					assertSingleBuilderMethod(builder, "make", String.class);
					assertSingleBuilderMethod(builder, "year", int.class);

					Class<?> usage = classLoader.getClass("io.jonasg.bob.test." + fixture + "Usage");
					assertEquals("volvo/2020", usage.getMethod("run").invoke(null));
				})
				.executeTest();
	}
}
