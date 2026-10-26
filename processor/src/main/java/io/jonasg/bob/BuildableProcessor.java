package io.jonasg.bob;

import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.definitions.TypeDefinitionFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("io.jonasg.bob.Buildable")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public final class BuildableProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		var elementUtils = processingEnv.getElementUtils();
		Types types = processingEnv.getTypeUtils();

		var typeDefinitionFactory = new TypeDefinitionFactory(elementUtils, types);
		Elements elements = processingEnv.getElementUtils();

		TypeMirror defaultsAsInnerClassMarker = elements
				.getTypeElement(Buildable.DefaultsAsInnerClass.class.getCanonicalName())
				.asType();

		// keyed by qualified name: TypeMirror equality is not guaranteed and a generic
		// buildable (Car<T>) never equals the raw type referenced in
		// @Defaults(Car.class)
		Map<String, DefaultValues> defaultValuesForBuildable = new HashMap<>();
		Map<String, TypeElement> defaultsHolderForBuildable = new HashMap<>();
		for (Element element : roundEnv.getElementsAnnotatedWith(Buildable.Defaults.class)) {
			Map.Entry<TypeMirror, TypeMirror> mapping = this.mapDefaults(element, types, defaultsAsInnerClassMarker);
			String buildableName = ((TypeElement) types.asElement(mapping.getKey())).getQualifiedName().toString();
			TypeElement defaultsHolder = (TypeElement) types.asElement(mapping.getValue());
			TypeElement existing = defaultsHolderForBuildable.putIfAbsent(buildableName, defaultsHolder);
			if (existing != null) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						"Multiple @Buildable.Defaults classes for %s: %s and %s"
								.formatted(buildableName, existing.getQualifiedName(),
										defaultsHolder.getQualifiedName()),
						element);
				continue;
			}
			defaultValuesForBuildable.put(buildableName, new DefaultValues(defaultsHolder));
		}

		var builderGenerator = new BuilderGenerator(processingEnv.getFiler(),
				defaultValuesForBuildable);

		roundEnv.getElementsAnnotatedWith(Buildable.class)
				.forEach(element -> {
					Buildable buildable = element.getAnnotation(Buildable.class);
					TypeDefinition sourceDefinition = typeDefinitionFactory.typeDefinitionForElement(element);
					boolean isInNullMarkedScope = isInNullMarkedScope(element);
					try {
						builderGenerator.generate(sourceDefinition, buildable, processingEnv.getTypeUtils(),
								isInNullMarkedScope);
					} catch (BuilderValidationException e) {
						for (String error : e.errors()) {
							processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, error, element);
						}
						for (String warning : e.warnings()) {
							processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, warning, element);
						}
					} catch (Exception e) {
						processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
					}
				});
		return true;
	}

	static boolean isInNullMarkedScope(Element element) {
		Element current = element;
		while (current != null && current.getKind() != javax.lang.model.element.ElementKind.PACKAGE) {
			boolean hasNullMarked = hasAnnotation(current, "org.jspecify.annotations.NullMarked");
			boolean hasNullUnmarked = hasAnnotation(current, "org.jspecify.annotations.NullUnmarked");
			if (hasNullMarked && !hasNullUnmarked)
				return true;
			if (hasNullUnmarked && !hasNullMarked)
				return false;
			current = current.getEnclosingElement();
		}
		if (current != null && current.getKind() == javax.lang.model.element.ElementKind.PACKAGE) {
			return hasAnnotation(current, "org.jspecify.annotations.NullMarked")
					&& !hasAnnotation(current, "org.jspecify.annotations.NullUnmarked");
		}
		return false;
	}

	private static boolean hasAnnotation(Element element, String qualifiedName) {
		return element.getAnnotationMirrors().stream()
				.anyMatch(a -> qualifiedName.equals(
						a.getAnnotationType().asElement().asType().toString()));
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	private Map.Entry<TypeMirror, TypeMirror> mapDefaults(Element element,
			Types types,
			TypeMirror defaultsAsInnerClassMarker) {
		if (!(element instanceof TypeElement defaultsHolder)) {
			throw new IllegalStateException("@Defaults must be on a class");
		}

		Buildable.Defaults defaultsAnnotation = defaultsHolder.getAnnotation(Buildable.Defaults.class);

		try {
			defaultsAnnotation.value();
		} catch (MirroredTypeException ex) {

			// here we are basically getting the Buildable.Defaults.value
			TypeMirror defaultsAnnotationValue = ex.getTypeMirror();

			// If the Buildable.Defaults.value is Buildable.DefaultsAsInnerClass
			if (types.isSameType(defaultsAnnotationValue, defaultsAsInnerClassMarker)) {
				// No explicit Buildable type defined as Defaults(value) param
				// hence it is assumed that the Defaults class is defined
				// as an inner class of the Buildable type

				Element enclosing = defaultsHolder.getEnclosingElement();
				if (!(enclosing instanceof TypeElement enclosingEl)) {
					throw new IllegalStateException(
							"@Buildable.Defaults must be defined as inner class of a buildable type or have an explicit buildable type set (through value parameter)");
				}

				if (enclosingEl.getAnnotation(Buildable.class) == null) {
					throw new IllegalStateException(
							"@Buildable.Defaults without explicit Buildable type set (through value parameter) " +
									"must be inner class of a @Buildable class");
				}

				return Map.entry(enclosingEl.asType(), defaultsHolder.asType());
			} else {

				return Map.entry(defaultsAnnotationValue, defaultsHolder.asType());
			}
		}

		throw new IllegalStateException("An unexpected error occurred while processing @Defaults annotation");
	}
}
