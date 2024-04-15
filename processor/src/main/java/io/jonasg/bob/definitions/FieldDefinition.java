package io.jonasg.bob.definitions;

import java.util.List;
import java.util.Objects;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

public record FieldDefinition(String name,
							  List<? extends AnnotationMirror> annotations,
							  TypeMirror type) {
	public <T> boolean isAnnotatedWith(Class<T> type) {
		return annotations.stream()
				.anyMatch(a -> Objects.equals(type.getName().replaceAll("\\$", "."),
						a.getAnnotationType().asElement().asType().toString()));
	}
}
