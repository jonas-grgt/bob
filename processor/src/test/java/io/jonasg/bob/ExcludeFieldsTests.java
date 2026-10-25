package io.jonasg.bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.toolisticon.cute.Cute;

/**
 * Excluded fields must not be validated in STRICT builders and are passed as
 * their default; a field can't be both excluded and mandatory (issue #110).
 */
class ExcludeFieldsTests {

	private static Object invoke(Object target, String name, Object... args) throws Exception {
		Method method = Arrays.stream(target.getClass().getMethods())
				.filter(m -> m.getName().equals(name) && m.getParameterCount() == args.length)
				.findFirst()
				.orElseThrow(() -> new AssertionError("No method " + name + " on " + target.getClass()));
		try {
			return method.invoke(target, args);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof Exception cause) {
				throw cause;
			}
			throw e;
		}
	}

	private static boolean hasMethod(Class<?> type, String name) {
		return Arrays.stream(type.getMethods()).anyMatch(m -> m.getName().equals(name));
	}

	@Test
	void strictBuilderWithExcludedPrimitiveFieldBuildsWithDefault() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/ExcludeFields/StrictWithExcludedPrimitive/StrictWithExcludedPrimitive.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClass("io.jonasg.bob.test.StrictWithExcludedPrimitiveBuilder")
				.testedSuccessfullyBy((builderClass, classLoader) -> {
					if (hasMethod(builderClass, "year")) {
						throw new AssertionError("Excluded field 'year' must not have a builder setter");
					}

					Object builder = builderClass.getConstructor().newInstance();
					invoke(builder, "make", "Volvo");
					Object car = invoke(builder, "build");

					assertEquals("Volvo", invoke(car, "getMake"));
					assertEquals(0, invoke(car, "getYear"));

					// non-excluded mandatory fields are still validated
					Object emptyBuilder = builderClass.getConstructor().newInstance();
					Exception missing = assertThrows(Exception.class, () -> invoke(emptyBuilder, "build"));
					assertEquals("io.jonasg.bob.MandatoryFieldMissingException", missing.getClass().getName());
				})
				.executeTest();
	}

	@Test
	void strictBuilderWithExcludedReferenceFieldsBuildsWithNull() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/ExcludeFields/StrictWithExcludedReference/StrictWithExcludedReference.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClass("io.jonasg.bob.test.StrictWithExcludedReferenceBuilder")
				.testedSuccessfullyBy((builderClass, classLoader) -> {
					if (hasMethod(builderClass, "model") || hasMethod(builderClass, "color")) {
						throw new AssertionError("Excluded fields must not have builder setters");
					}

					Object builder = builderClass.getConstructor().newInstance();
					invoke(builder, "make", "Volvo");
					Object car = invoke(builder, "build");

					assertEquals("Volvo", invoke(car, "getMake"));
					assertNull(invoke(car, "getModel"));
					assertNull(invoke(car, "getColor"));
				})
				.executeTest();
	}

	@Test
	void fieldInBothExcludeFieldsAndMandatoryFieldsFailsCompilation() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/ExcludeFields/ExcludedAndMandatory/ExcludedAndMandatory.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationFails()
				.andThat()
				.compilerMessage()
				.ofKindError()
				.contains("year", "excludeFields", "mandatoryFields")
				.executeTest();
	}
}
