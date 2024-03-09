package io.jonasg.bob.specs;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.TypeDefinition;
import com.squareup.javapoet.TypeSpec;

public abstract class TypeSpecFactory {
    public static TypeSpec produce(TypeDefinition source, Buildable buildable) {
        if (hasFinalFields(source))
            return FieldsInsideBuilderTypeSpecFactory.produce(source, buildable);
        else
            return InstanceInsideBuilderTypeSpecFactory.produce(source, buildable);
    }

    private static boolean hasFinalFields(TypeDefinition source) {
        for (FieldDefinition field : source.fields())
            if (field.isFinal())
                return true;
        return false;
    }
}
