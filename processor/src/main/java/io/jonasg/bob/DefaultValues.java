package io.jonasg.bob;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;

class DefaultValues {

	private final Element element;

	public DefaultValues(Element element) {
		this.element = element;
	}

	public Optional<VariableElement> forField(String name) {
		return this.element.getEnclosedElements().stream()
				.filter(e -> e.getKind() == ElementKind.FIELD)
				.map(VariableElement.class::cast)
				.filter(f -> f.getSimpleName().contentEquals(name))
				.findFirst();
	}

	public String packageName() {
		var ee = this.element.getEnclosingElement();
		while (!(ee instanceof PackageElement p)) {
			ee = ee.getEnclosingElement();
		}
		return p.getQualifiedName().toString();
	}

	public TypeMirror asType() {
		return this.element.asType();
	}
}
