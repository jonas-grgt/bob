package io.jonasg.bob.definitions;

import java.util.List;

import javax.lang.model.type.TypeMirror;

public record MethodDefinition(String name,
							   List<TypeMirror> parameters) {
}
