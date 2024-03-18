package io.jonasg.bob.definitions;


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

	public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

		private String name;

		private TypeMirror type;

		public Builder name(String name) {
            this.name = name;
            return this;
        }

        public ParameterDefinition build() {
            return new ParameterDefinition(this.type, this.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterDefinition that = (ParameterDefinition) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
