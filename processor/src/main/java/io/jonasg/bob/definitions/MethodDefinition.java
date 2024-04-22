package io.jonasg.bob.definitions;

import java.util.List;
import java.util.Objects;

import javax.lang.model.type.TypeMirror;

public final class MethodDefinition {
	private final String name;
	private final List<TypeMirror> parameters;

	public MethodDefinition(String name,
			List<TypeMirror> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public String name() {
		return name;
	}

	public List<TypeMirror> parameters() {
		return parameters;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (MethodDefinition) obj;
		return Objects.equals(this.name, that.name) &&
				Objects.equals(this.parameters, that.parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, parameters);
	}

	@Override
	public String toString() {
		return "MethodDefinition[" +
				"name=" + name + ", " +
				"parameters=" + parameters + ']';
	}

}
