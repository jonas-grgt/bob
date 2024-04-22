package io.jonasg.bob;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.definitions.TypeDefinitionFactory;

@SupportedAnnotationTypes("io.jonasg.bob.Buildable")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public final class BuildableProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Elements elementUtils = processingEnv.getElementUtils();

		BuilderGenerator builderGenerator = new BuilderGenerator(processingEnv.getFiler());
		TypeDefinitionFactory typeDefinitionFactory = new TypeDefinitionFactory(elementUtils);

		roundEnv.getElementsAnnotatedWith(Buildable.class)
				.forEach(element -> {
					Buildable buildable = element.getAnnotation(Buildable.class);
					TypeDefinition sourceDefinition = typeDefinitionFactory.typeDefinitionForElement(element);
					try {
						builderGenerator.generate(sourceDefinition, buildable, processingEnv.getTypeUtils());
					} catch (Exception e) {
						processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
					}
				});
		return true;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
