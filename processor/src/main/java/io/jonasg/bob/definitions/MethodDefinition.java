package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;
import java.util.List;

public record MethodDefinition(
		String name,
		List<TypeMirror> parameters
) {
}
