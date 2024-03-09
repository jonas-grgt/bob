package io.jonasg.bob.definitions;


public class ParameterDefinition {

    private String name;

    public ParameterDefinition(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    private ParameterDefinition() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private ParameterDefinition instance = new ParameterDefinition();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public ParameterDefinition build() {
            ParameterDefinition result = instance;
            instance = null;
            return result;
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
