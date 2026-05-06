package io.jonasg.bob;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultsProviderTest {

	record DefaultCase(String field, Class<?> type, Object expected) {
	}

	static Stream<DefaultCase> existingFieldCases() {
		return Stream.of(
				// direct matches
				new DefaultCase("name", String.class, "Jonas"),
				new DefaultCase("age", int.class, 7),
				new DefaultCase("longAge", long.class, 9L),
				new DefaultCase("active", boolean.class, true),
				new DefaultCase("byteVal", byte.class, (byte) 1),
				new DefaultCase("shortVal", short.class, (short) 2),
				new DefaultCase("charVal", char.class, 'a'),
				new DefaultCase("floatVal", float.class, 1.5f),
				new DefaultCase("doubleVal", double.class, 2.5d),

				// boxing / unboxing cases
				new DefaultCase("age", Integer.class, 7),
				new DefaultCase("integerAge", int.class, 8),
				new DefaultCase("longAge", Long.class, 9L),
				new DefaultCase("active", Boolean.class, true),
				new DefaultCase("byteVal", Byte.class, (byte) 1),
				new DefaultCase("shortVal", Short.class, (short) 2),
				new DefaultCase("charVal", Character.class, 'a'),
				new DefaultCase("floatVal", Float.class, 1.5f),
				new DefaultCase("doubleVal", Double.class, 2.5d));
	}

	static Stream<DefaultCase> fallbackCases() {
		return Stream.of(
				// primitives → language defaults
				new DefaultCase("unknown", int.class, 0),
				new DefaultCase("unknown", long.class, 0L),
				new DefaultCase("unknown", boolean.class, false),
				new DefaultCase("unknown", byte.class, (byte) 0),
				new DefaultCase("unknown", short.class, (short) 0),
				new DefaultCase("unknown", char.class, '\0'),
				new DefaultCase("unknown", float.class, 0f),
				new DefaultCase("unknown", double.class, 0d),

				// boxed types → null (your design)
				new DefaultCase("unknown", Integer.class, null),
				new DefaultCase("unknown", Long.class, null),
				new DefaultCase("unknown", Boolean.class, null),
				new DefaultCase("unknown", Byte.class, null),
				new DefaultCase("unknown", Short.class, null),
				new DefaultCase("unknown", Character.class, null),
				new DefaultCase("unknown", Float.class, null),
				new DefaultCase("unknown", Double.class, null),
				new DefaultCase("unknown", String.class, null));
	}

	private static final DefaultsProvider provider = DefaultsProvider.of("io.jonasg.bob.TestDefaults");

	@ParameterizedTest(name = "{0}")
	@MethodSource("existingFieldCases")
	void returnsValueFromDefaultsClass(DefaultCase testCase) {
		var result = provider.get(testCase.field(), testCase.type());

		assertThat(result).isEqualTo(testCase.expected());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("fallbackCases")
	void fallsBackToDefaultValue(DefaultCase testCase) {
		var result = provider.get(testCase.field(), testCase.type());

		assertThat(result).isEqualTo(testCase.expected());
	}

	@Nested
	class WhenDefaultsClassDoesNotExist {

		private final DefaultsProvider provider = DefaultsProvider.of("does.not.Exist");

		@Test
		void fallsBackToPrimitiveIntDefault() {
			var result = provider.get("age", int.class);

			assertThat(result).isZero();
		}

		@Test
		void fallsBackToIntegerDefault() {
			var result = provider.get("integerAge", Integer.class);

			assertThat(result).isNull();
		}

		@Test
		void fallsBackToStringDefault() {
			var result = provider.get("name", String.class);

			assertThat(result).isNull();
		}
	}

	@Nested
	class WhenFieldIsNotStatic {

		@SuppressWarnings("unused")
		static class InvalidDefaults {
			public String notStatic = "fail";
		}

		private final DefaultsProvider provider = DefaultsProvider.of(InvalidDefaults.class.getName());

		@Test
		void ignoresFieldAndFallsBackToDefault() {
			var result = provider.get("notStatic", String.class);

			assertThat(result).isNull();
		}
	}
}
