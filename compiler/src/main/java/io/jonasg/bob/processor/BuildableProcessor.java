package io.jonasg.bob.processor;


import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.TypeDefinitionFactory;

@SupportedAnnotationTypes("io.jonasg.bob.Buildable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class BuildableProcessor extends AbstractProcessor {

	@Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Elements elementUtils = processingEnv.getElementUtils();

        BuilderGenerator builderGenerator = new BuilderGenerator(processingEnv.getFiler());
        TypeDefinitionFactory typeDefinitionFactory = new TypeDefinitionFactory(elementUtils);

		roundEnv.getElementsAnnotatedWith(Buildable.class).forEach(elem -> {
			Buildable buildable = elem.getAnnotation(Buildable.class);
			TypeDefinition sourceDefinition = typeDefinitionFactory.typeDefinitionForElement(elem);
			builderGenerator.generate(sourceDefinition, buildable);
		});
        return true;
    }


}
