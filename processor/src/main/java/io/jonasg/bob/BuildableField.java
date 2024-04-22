package io.jonasg.bob;

import java.util.Objects;
import java.util.Optional;

import javax.lang.model.type.TypeMirror;

/**
 * Represents a field that is buildable
 *
 * {@link BuildableField#name} the name of the field as declared in the type
 * that will be built
 * {@link BuildableField#isConstructorArgument} indicates if the field can be
 * set through the constructor
 * {@link BuildableField#setterMethodName} the name of the setter method to
 * access the field.
 * {@link BuildableField#type} the type of the field
 */
public final class BuildableField {
	private final String name;
	private final boolean isConstructorArgument;
	private final boolean isMandatory;
	private final Optional<String> setterMethodName;
	private final TypeMirror type;

	public BuildableField(
			String name,
			boolean isConstructorArgument,
			boolean isMandatory,
			Optional<String> setterMethodName,
			TypeMirror type) {
		this.name = name;
		this.isConstructorArgument = isConstructorArgument;
		this.isMandatory = isMandatory;
		this.setterMethodName = setterMethodName;
		this.type = type;
	}

	public static BuildableField fromConstructor(String fieldName, TypeMirror type) {
		return new BuildableField(fieldName, true, false, Optional.empty(), type);
	}

	public static BuildableField fromSetter(String fieldName, boolean fieldIsMandatory, String setterMethodName,
			TypeMirror type) {
		return new BuildableField(fieldName, false, fieldIsMandatory, Optional.of(setterMethodName), type);
	}

	public String name() {
		return name;
	}

	public boolean isConstructorArgument() {
		return isConstructorArgument;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public Optional<String> setterMethodName() {
		return setterMethodName;
	}

	public TypeMirror type() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (BuildableField) obj;
		return Objects.equals(this.name, that.name) &&
				this.isConstructorArgument == that.isConstructorArgument &&
				this.isMandatory == that.isMandatory &&
				Objects.equals(this.setterMethodName, that.setterMethodName) &&
				Objects.equals(this.type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, isConstructorArgument, isMandatory, setterMethodName, type);
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
