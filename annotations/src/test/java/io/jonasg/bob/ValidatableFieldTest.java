package io.jonasg.bob;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidatableFieldTest {

	@Nested
	class NoneNullableValidatableFieldTest {

		@Test
		void throwExceptionWhenFieldValueIsNotSet() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNoneNullableField("name", "Person");

			// when
			ThrowingCallable whenOrElseThrowIsCalled = nameField::orElseThrow;

			// then
			Assertions.assertThatThrownBy(whenOrElseThrowIsCalled)
					.isInstanceOf(MandatoryFieldMissingException.class)
					.hasMessage("Mandatory field (name) not set when building type (Person)");
		}

		@Test
		void returnFieldValue() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNoneNullableField("name", "Person");
			nameField.set("John");

			// when
			String value = nameField.orElseThrow();

			// then
			Assertions.assertThat(value)
					.isEqualTo("John");
		}

		@Test
		void throwExceptionWhenNotNullableRequiredFieldSetToNull() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNoneNullableField("name", "Person");
			nameField.set(null);

			// when
			ThrowingCallable whenOrElseThrowIsCalled = nameField::orElseThrow;

			// then
			Assertions.assertThatThrownBy(whenOrElseThrowIsCalled)
					.isInstanceOf(MandatoryFieldMissingException.class)
					.hasMessage("Mandatory field (name) not set when building type (Person)");
		}
	}

	@Nested
	class NullableValidatableFieldTest {

		@Test
		void throwExceptionWhenFieldValueIsNotSet() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNullableField("name", "Person");

			// when
			ThrowingCallable whenOrElseThrowIsCalled = nameField::orElseThrow;

			// then
			Assertions.assertThatThrownBy(whenOrElseThrowIsCalled)
					.isInstanceOf(MandatoryFieldMissingException.class)
					.hasMessage("Mandatory field (name) not set when building type (Person)");
		}

		@Test
		void returnFieldValue() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set("John");

			// when
			String value = nameField.orElseThrow();

			// then
			Assertions.assertThat(value)
					.isEqualTo("John");
		}

		@Test
		void returnFieldValueWhenSetToNull() {
			// given
			ValidatableField<String> nameField = ValidatableField.ofNullableField("name", "Person");
			nameField.set(null);

			// when
			String value = nameField.orElseThrow();

			// then
			Assertions.assertThat(value)
					.isEqualTo(null);
		}
	}
}
