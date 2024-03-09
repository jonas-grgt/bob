package io.jonasg.bob.definitions;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

public class FieldDefinition {

    private final String name;
    private final Set<Modifier> modifiers;
    private final TypeMirror type;

    public FieldDefinition(String name, Set<Modifier> modifiers, TypeMirror type) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public TypeMirror type() {
        return type;
    }

    public boolean isPrivate() {
        return modifiers.contains(Modifier.PRIVATE);
    }

    public boolean isProtected() {
        return modifiers.contains(Modifier.PROTECTED);
    }

    public boolean isFinal() {
        return modifiers.contains(Modifier.FINAL);
    }

    public boolean isPublic() {
        return modifiers.contains(Modifier.PUBLIC);
    }

    public boolean isPackageLocal() {
        return modifiers.isEmpty();
    }
}
