package io.jonasg.bob;

import java.util.Optional;

import javax.lang.model.type.TypeMirror;

/**
 * Represents a field that is buildable
 *
 * @param name the name of the field as declared in the type that will be built
 * @param isConstructorArgument indicates if the field can be set through the constructor
 * @param setterMethodName the name of the setter method to access the field.
 * @param type the type of the field
 */
public record BuildableField(
		String name,
		boolean isConstructorArgument,
		boolean isMandatory,
		Optional<String> setterMethodName,
		TypeMirror type) {

	public static BuildableField fromConstructor(String fieldName, TypeMirror type) {
		return new BuildableField(fieldName, true, false, Optional.empty(), type);
	}

	public static BuildableField fromSetter(String fieldName, boolean fieldIsMandatory, String setterMethodName, TypeMirror type) {
		return new BuildableField(fieldName, false, fieldIsMandatory, Optional.of(setterMethodName), type);
	}
}
