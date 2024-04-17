package io.jonasg.bob;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RequiredFieldTest {

	@Nested
	class NotNullableRequiredFieldTest {

		@Test
		void throwExceptionWhenFieldValueIsNotSet() {
			// given
			RequiredField<String> nameField = RequiredField.notNullableOfNameWithinType("name", "Person");

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
			RequiredField<String> nameField = RequiredField.notNullableOfNameWithinType("name", "Person");
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
			RequiredField<String> nameField = RequiredField.notNullableOfNameWithinType("name", "Person");
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
	class NullableRequiredFieldTest {

		@Test
		void throwExceptionWhenFieldValueIsNotSet() {
			// given
			RequiredField<String> nameField = RequiredField.nullableOfNameWithinType("name", "Person");

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
			RequiredField<String> nameField = RequiredField.nullableOfNameWithinType("name", "Person");
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
			RequiredField<String> nameField = RequiredField.nullableOfNameWithinType("name", "Person");
			nameField.set(null);

			// when
			String value = nameField.orElseThrow();

			// then
			Assertions.assertThat(value)
					.isEqualTo(null);
		}
	}
}
