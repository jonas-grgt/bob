package io.jonasg.bob.definitions;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ParameterDefinition {

	private final TypeMirror type;

	private final String name;

	private final List<? extends AnnotationMirror> annotations;

	public ParameterDefinition(TypeMirror type, String name) {
		this(type, name, Collections.emptyList());
	}

	public ParameterDefinition(TypeMirror type, String name,
			List<? extends AnnotationMirror> annotations) {
		this.type = type;
		this.name = name;
		this.annotations = annotations;
	}

	public String name() {
		return name;
	}

	public TypeMirror type() {
		return type;
	}

	public boolean isAnnotatedWith(Class<?> type) {
		return annotations.stream()
				.anyMatch(a -> Objects.equals(type.getName().replace('$', '.'),
						a.getAnnotationType().asElement().asType().toString()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ParameterDefinition that = (ParameterDefinition) o;

		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
