package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;
import java.util.Objects;

public final class SetterMethodDefinition {
	private final String methodName;
	private final FieldDefinition field;
	private final TypeMirror type;

	public SetterMethodDefinition(String methodName,
			FieldDefinition field,
			TypeMirror type) {
		this.methodName = methodName;
		this.field = field;
		this.type = type;
	}

	public String methodName() {
		return methodName;
	}

	public FieldDefinition field() {
		return field;
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
		var that = (SetterMethodDefinition) obj;
		return Objects.equals(this.methodName, that.methodName) &&
				Objects.equals(this.field, that.field) &&
				Objects.equals(this.type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(methodName, field, type);
	}

	@Override
	public String toString() {
		return "SetterMethodDefinition[" +
				"methodName=" + methodName + ", " +
				"field=" + field + ", " +
				"type=" + type + ']';
	}

}
