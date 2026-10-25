package io.jonasg.bob.definitions;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;

public record MethodDefinition(
		String name,
		List<TypeMirror> parameters,
		Set<Modifier> modifiers,
		String declaringPackage
) {

	public boolean isAccessibleFrom(String packageName) {
		return Accessibility.isAccessible(modifiers, declaringPackage, packageName);
	}
}
