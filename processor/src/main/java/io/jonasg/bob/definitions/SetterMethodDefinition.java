package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;
import java.util.Objects;

public record SetterMethodDefinition(
		String methodName,
		FieldDefinition field,
		TypeMirror type
) {
}
