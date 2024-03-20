package io.jonasg.bob.definitions;

public class SimpleTypeDefinition {

    protected String typeName;

    protected String packageName;

    protected String parent;

    public SimpleTypeDefinition(String typeName, String packageName) {
        this.typeName = typeName;
        this.packageName = packageName;
    }

    SimpleTypeDefinition() {}

    public String typeName() {
        return typeName;
    }

    public String packageName() {
        return packageName;
    }

    public String fullTypeName() {
        return (parent != null ? parent + "."  : "") + typeName;
    }

}
