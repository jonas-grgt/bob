package io.jonasg.bob;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatableFieldTest {

	@Nested
	class NoneNullableValidatableFieldTest {

		@Test
		void isNotValidWhenFieldValueIsNotSet() {
			var nameField = ValidatableField.ofNoneNullableField("name", "Person");
			assertThat(nameField.isValid()).isFalse();
		}

		@Test
		void isValidWhenFieldValueIsSet() {
			var nameField = ValidatableField.ofNoneNullableField("name", "Person");
			nameField.set("John");

			assertThat(nameField.isValid()).isTrue();
		}

		@Test
		void returnValueWhenFieldValueIsSet() {
			var nameField = ValidatableField.ofNoneNullableField("name", "Person");
			nameField.set("John");

			assertThat(nameField.get()).isEqualTo("John");
		}

		@Test
		void isNotValidWhenSetToNull() {
			var nameField = ValidatableField.ofNoneNullableField("name", "Person");
			nameField.set(null);

			assertThat(nameField.isValid()).isFalse();
			assertThat(nameField.get()).isNull();
		}
	}

	@Nested
	class NullableValidatableFieldTest {

		@Test
		void isNotValidWhenFieldValueIsNotSet() {
			var nameField = ValidatableField.ofNullableField("name", "Person");
			assertThat(nameField.isValid()).isFalse();
		}

		@Test
		void isValidWhenFieldValueIsSet() {
			var nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set("John");

			assertThat(nameField.isValid()).isTrue();
		}

		@Test
		void returnValueWhenFieldValueIsSet() {
			var nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set("John");

			assertThat(nameField.get()).isEqualTo("John");
		}

		@Test
		void isValidWhenSetToNull() {
			var nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set(null);

			assertThat(nameField.isValid()).isTrue();
		}

		@Test
		void returnNullWhenSetToNull() {
			var nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set(null);

			assertThat(nameField.get()).isNull();
		}
	}
}
