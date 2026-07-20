package io.jonasg.bob;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import java.io.IOException;

class TypeWriter {
	private static final ClassName GENERATED_ANNOTATION = ClassName.get("javax.annotation.processing",
			"Generated");

	public static void write(Filer filer, String packageName, TypeSpec spec, String generatedBy) {
		TypeSpec generatedType = spec.toBuilder()
				.addAnnotation(AnnotationSpec.builder(GENERATED_ANNOTATION)
						.addMember("value", "$S", generatedBy)
						.build())
				.build();
		var javaFile = JavaFile.builder(packageName, generatedType)
				.build();
		try {
			javaFile.writeTo(filer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
