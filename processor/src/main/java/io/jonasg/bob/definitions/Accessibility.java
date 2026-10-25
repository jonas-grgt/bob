package io.jonasg.bob.definitions;

import javax.lang.model.element.Modifier;
import java.util.Set;

final class Accessibility {

	private Accessibility() {
	}

	/**
	 * Whether a member with the given modifiers, declared in
	 * {@code declaringPackage},
	 * can be called from a (non-subclass) type in {@code fromPackage}.
	 */
	static boolean isAccessible(Set<Modifier> modifiers, String declaringPackage, String fromPackage) {
		if (modifiers.contains(Modifier.PUBLIC)) {
			return true;
		}
		return !modifiers.contains(Modifier.PRIVATE) && declaringPackage.equals(fromPackage);
	}
}
