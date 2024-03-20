package io.jonasg.bob;

import javax.annotation.processing.Filer;

import com.squareup.javapoet.TypeSpec;
import io.jonasg.bob.definitions.TypeDefinition;

public class BuilderGenerator {

    private final Filer filer;

    public BuilderGenerator(Filer filer) {
        this.filer = filer;
    }

    public void generate(TypeDefinition typeDefinition, Buildable buildable) {
		TypeSpec typeSpec = TypeSpecFactory.produce(typeDefinition, buildable);
		String result;
		if (!buildable.packageName().isEmpty()) {
			result = buildable.packageName();
		} else {
			result = String.format("%s.builder", typeDefinition.packageName());
		}
		TypeWriter.write(filer, result, typeSpec);
    }

}
