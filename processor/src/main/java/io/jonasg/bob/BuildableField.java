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
public record BuildableField(
		String name,
		boolean isConstructorArgument,
		boolean isMandatory,
		boolean isOptional,
		Optional<String> setterMethodName,
		TypeMirror type
) {

	public static BuildableField fromConstructor(String fieldName, boolean isOptional,  TypeMirror type) {
		return new BuildableField(fieldName, true, false, isOptional, Optional.empty(), type);
	}

	public static BuildableField fromSetter(String fieldName, boolean fieldIsMandatory, String setterMethodName,
											TypeMirror type) {
		return new BuildableField(fieldName, false, fieldIsMandatory, false, Optional.of(setterMethodName), type);
	}


	@Override
	public String toString() {
		return "BuildableField[" +
			   "name=" + name + ", " +
			   "isConstructorArgument=" + isConstructorArgument + ", " +
			   "isMandatory=" + isMandatory + ", " +
			   "setterMethodName=" + setterMethodName + ", " +
			   "type=" + type + ']';
	}

}
