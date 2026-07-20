package io.jonasg.bob;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeVariableName;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("io.jonasg.bob.TestDefaults")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public final class TestDefaultsProcessor extends AbstractProcessor {

	private static final String GENERATED_BY = TestDefaultsProcessor.class.getCanonicalName();

	private static final ClassName MAP_CLASS = ClassName.get(Map.class);
	private static final ClassName HASHMAP_CLASS = ClassName.get("java.util", "HashMap");

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Types types = processingEnv.getTypeUtils();
		Elements elements = processingEnv.getElementUtils();

		TypeMirror defaultsAsInnerClassMarker = elements
				.getTypeElement(Buildable.DefaultsAsInnerClass.class.getCanonicalName())
				.asType();

		List<Map.Entry<String, String>> registryEntries = new ArrayList<>();

		for (Element element : roundEnv.getElementsAnnotatedWith(TestDefaults.class)) {
			Map.Entry<String, String> entry = mapTestDefaults(element, types, defaultsAsInnerClassMarker);
			if (entry != null) {
				registryEntries.add(entry);
			}
		}

		if (!registryEntries.isEmpty()) {
			generateTestDefaultsRegistry(registryEntries);
		}

		return true;
	}

	private Map.Entry<String, String> mapTestDefaults(Element element,
			Types types,
			TypeMirror defaultsAsInnerClassMarker) {
		if (!(element instanceof TypeElement defaultsHolder)) {
			throw new IllegalStateException("@TestDefaults must be on a class");
		}

		TestDefaults testDefaultsAnnotation = defaultsHolder.getAnnotation(TestDefaults.class);

		try {
			testDefaultsAnnotation.value();
		} catch (MirroredTypeException ex) {
			TypeMirror testDefaultsAnnotationValue = ex.getTypeMirror();

			if (types.isSameType(testDefaultsAnnotationValue, defaultsAsInnerClassMarker)) {
				Element enclosing = defaultsHolder.getEnclosingElement();
				if (enclosing instanceof TypeElement enclosingEl) {
					return Map.entry(
							enclosingEl.getQualifiedName().toString(),
							defaultsHolder.getQualifiedName().toString());
				}
			} else {
				TypeElement buildableType = (TypeElement) types.asElement(testDefaultsAnnotationValue);
				if (buildableType != null) {
					return Map.entry(
							buildableType.getQualifiedName().toString(),
							defaultsHolder.getQualifiedName().toString());
				}
			}
		}

		return null;
	}

	private void generateTestDefaultsRegistry(List<Map.Entry<String, String>> entries) {
		var registryType = ParameterizedTypeName.get(MAP_CLASS, TypeVariableName.get(String.class),
				TypeVariableName.get(String.class));

		var registryField = FieldSpec
				.builder(registryType, "REGISTRY", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
				.initializer("new $T<>()", HASHMAP_CLASS)
				.build();

		var staticBlock = CodeBlock.builder();
		for (var entry : entries) {
			staticBlock.addStatement("REGISTRY.put($S, $S)", entry.getKey(), entry.getValue());
		}

		var findMethod = MethodSpec.methodBuilder("findDefaultsClassName")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(String.class)
				.addParameter(String.class, "buildableTypeName")
				.addStatement("return REGISTRY.get(buildableTypeName)")
				.build();

		var typeSpec = TypeSpec.classBuilder("TestDefaultsRegistry")
				.addModifiers(Modifier.PUBLIC)
				.addJavadoc("Generated registry mapping buildable types to their test defaults classes.\n")
				.addField(registryField)
				.addStaticBlock(staticBlock.build())
				.addMethod(findMethod)
				.build();

		TypeWriter.write(processingEnv.getFiler(), "io.jonasg.bob", typeSpec, GENERATED_BY);
	}
}
