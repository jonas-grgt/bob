package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;

public record SetterMethodDefinition(String methodName,
									 FieldDefinition field,
									 TypeMirror type) {
}
