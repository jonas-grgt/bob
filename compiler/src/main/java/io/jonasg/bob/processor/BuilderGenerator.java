package io.jonasg.bob.processor;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.TypeWriter;
import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.specs.InstanceInsideBuilderTypeSpecFactory;
import io.jonasg.bob.specs.TypeSpecFactory;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;

public class BuilderGenerator {

    private final Filer filer;

    public BuilderGenerator(Filer filer) {
        this.filer = filer;
    }

    public void generate(TypeDefinition source, Buildable buildable) {
        TypeSpec typeSpec = TypeSpecFactory.produce(source, buildable);
        TypeWriter.write(filer, InstanceInsideBuilderTypeSpecFactory.builderPackage(source, buildable), typeSpec);
    }

}
