package io.jonasg.bob;

import javax.lang.model.type.TypeMirror;
import java.util.Optional;

/**
 * Represents a field that is buildable
 * <p>
 * {@link BuildableField#name} the name of the field as declared in the type
 * that will be built
 * {@link BuildableField#isConstructorArgument} indicates if the field can be
 * set through the constructor
 * {@link BuildableField#setterMethodName} the name of the setter method to
 * access the field.
 * {@link BuildableField#type} the type of the field
 */
record BuildableField(
		String name,
		boolean isConstructorArgument,
		boolean isMandatory,
		boolean isOptional,
		boolean isNullable,
		Optional<String> setterMethodName,
		TypeMirror type
) {

	public static BuildableField fromConstructor(String fieldName, boolean isMandatory, boolean isOptional, boolean isNullable, TypeMirror type) {
		return new BuildableField(fieldName, true, isMandatory, isOptional, isNullable, Optional.empty(), type);
	}

	public static BuildableField fromSetter(String fieldName, boolean fieldIsMandatory, boolean isNullable, String setterMethodName,
											TypeMirror type) {
		return new BuildableField(fieldName, false, fieldIsMandatory, false, isNullable, Optional.of(setterMethodName), type);
	}


	@Override
	public String toString() {
		return "BuildableField[" +
			   "name=" + name + ", " +
			   "isConstructorArgument=" + isConstructorArgument + ", " +
			   "isMandatory=" + isMandatory + ", " +
			   "isOptional=" + isOptional + ", " +
			   "isNullable=" + isNullable + ", " +
			   "setterMethodName=" + setterMethodName + ", " +
			   "type=" + type + ']';
	}

}
