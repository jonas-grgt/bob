package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;

public class FieldDefinition {

	private final String name;

	private final TypeMirror type;

	public FieldDefinition(String name, TypeMirror type) {
		this.name = name;
		this.type = type;
	}

	public String name() {
		return name;
	}

	public TypeMirror type() {
		return type;
	}
}
