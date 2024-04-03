package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;

public record FieldDefinition(String name, TypeMirror type) {
}
