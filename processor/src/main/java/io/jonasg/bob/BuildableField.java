package io.jonasg.bob;

import java.util.Optional;

import javax.lang.model.type.TypeMirror;

/**
 * Represents a field that is buildable
 *
 * @param fieldName the name of the field as declared in the type that will be built
 * @param isConstructorArgument indicates if the field can be set through the constructor
 * @param setterMethodName the name of the setter method to access the field.
 * @param type the type of the field
 */
public record BuildableField(
		String fieldName,
		boolean isConstructorArgument,
		Optional<String> setterMethodName,
		TypeMirror type) {

	public static BuildableField fromConstructor(String fieldName, TypeMirror type) {
		return new BuildableField(fieldName, true, Optional.empty(), type);
	}

	public boolean isMandatory() {
		return isConstructorArgument;
	}
}
