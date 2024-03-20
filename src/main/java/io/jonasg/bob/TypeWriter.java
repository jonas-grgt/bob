package io.jonasg.bob;

import java.io.IOException;

import javax.annotation.processing.Filer;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

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
