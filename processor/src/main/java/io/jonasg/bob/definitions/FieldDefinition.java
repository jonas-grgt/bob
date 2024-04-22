package io.jonasg.bob.definitions;

import java.util.List;
import java.util.Objects;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

public final class FieldDefinition {
	private final String name;
	private final List<? extends AnnotationMirror> annotations;
	private final TypeMirror type;

	public FieldDefinition(String name,
			List<? extends AnnotationMirror> annotations,
			TypeMirror type) {
		this.name = name;
		this.annotations = annotations;
		this.type = type;
	}

	public <T> boolean isAnnotatedWith(Class<T> type) {
		return annotations.stream()
				.anyMatch(a -> Objects.equals(type.getName().replaceAll("\\$", "."),
						a.getAnnotationType().asElement().asType().toString()));
	}

	public String name() {
		return name;
	}

	public List<? extends AnnotationMirror> annotations() {
		return annotations;
	}

	public TypeMirror type() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (FieldDefinition) obj;
		return Objects.equals(this.name, that.name) &&
				Objects.equals(this.annotations, that.annotations) &&
				Objects.equals(this.type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, annotations, type);
	}

	@Override
	public String toString() {
		return "FieldDefinition[" +
				"name=" + name + ", " +
				"annotations=" + annotations + ", " +
				"type=" + type + ']';
	}

}
