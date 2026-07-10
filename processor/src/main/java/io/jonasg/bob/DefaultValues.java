package io.jonasg.bob;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
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

	public Optional<TypeMirror> supplierTypeArgument(String fieldName) {
		Optional<VariableElement> field = forField(fieldName);
		if (field.isEmpty()) {
			return Optional.empty();
		}
		TypeMirror fieldType = field.get().asType();
		if (fieldType instanceof DeclaredType declaredType
				&& declaredType.asElement() instanceof TypeElement typeElement
				&& typeElement.getQualifiedName()
						.contentEquals("java.util.function.Supplier")
				&& declaredType.getTypeArguments().size() == 1) {
			return Optional.of(declaredType.getTypeArguments().get(0));
		}
		return Optional.empty();
	}
}
