package io.jonasg.bob.definitions;


import java.util.Objects;

import javax.lang.model.type.TypeMirror;

public class ParameterDefinition {

	private final TypeMirror type;

	private final String name;

    public ParameterDefinition(TypeMirror type, String name) {
		this.type = type;
		this.name = name;
    }

    public String name() {
        return name;
    }

	public TypeMirror type() {
		return type;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterDefinition that = (ParameterDefinition) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
