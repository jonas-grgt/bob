package io.jonasg.bob;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.TypeWriter;
import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.TypeSpecFactory;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;

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
