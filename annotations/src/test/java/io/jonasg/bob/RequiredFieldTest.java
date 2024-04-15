package io.jonasg.bob;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

class RequiredFieldTest {

	@Test
	void throwIllegalBuildExceptionWhenFieldValueIsNotSet() {
		// given
		RequiredField<String> nameField = RequiredField.ofNameWithinType("name", "Person");

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
		RequiredField<String> nameField = RequiredField.ofNameWithinType("name", "Person");
		nameField.set("John");

		// when
		String value = nameField.orElseThrow();

		// then
		Assertions.assertThat(value)
				.isEqualTo("John");
	}

}
