package io.jonasg.bob.definitions;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

public final class ConstructorDefinition {

	private final List<ParameterDefinition> parameters;

	private final Set<Modifier> modifiers;

	private final List<? extends AnnotationMirror> annotations;

	public ConstructorDefinition(List<ParameterDefinition> parameters,
			Set<Modifier> modifiers,
			List<? extends AnnotationMirror> annotations) {
		this.parameters = parameters;
		this.modifiers = modifiers;
		this.annotations = annotations;
	}

	public boolean isPrivate() {
		return modifiers.contains(Modifier.PRIVATE);
	}

	public List<ParameterDefinition> parameters() {
		return parameters;
	}

	public boolean isAnnotatedWith(Class<?> type) {
		return annotations.stream()
				.anyMatch(a -> Objects.equals(type.getName().replaceAll("\\$", "."),
						a.getAnnotationType().asElement().asType().toString()));
	}
}
