package io.jonasg.bob;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import java.io.IOException;

public class TypeWriter {
    public static void write(Filer filer, String packageName, TypeSpec spec) {
        JavaFile javaFile = JavaFile.builder(packageName, spec)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
