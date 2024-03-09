package io.jonasg.bob.specs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.jonasg.bob.Buildable;
import io.jonasg.bob.definitions.ConstructorDefinition;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.MethodDefinition;
import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.utils.Formatter;

public class InstanceInsideBuilderTypeSpecFactory extends BaseTypeSpecFactory {

    private InstanceInsideBuilderTypeSpecFactory(TypeDefinition source, Buildable buildable) {
        super(source, buildable);
    }

    public static TypeSpec produce(TypeDefinition source, Buildable buildable) {
        return new InstanceInsideBuilderTypeSpecFactory(source, buildable).typeSpec();
    }

    private TypeName classNameWithoutGenerics(TypeDefinition definition) {
        return ClassName.get(definition.packageName(), definition.fullTypeName());
    }

    @Override
    protected List<MethodSpec> setters() {
        List<MethodSpec> setters = new ArrayList<>();
        for (FieldDefinition field : source.fields()) {
            if (!field.isFinal() && notExcluded(field)) {
                MethodSpec.Builder builder = MethodSpec.methodBuilder(fieldName(field.name()))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(builderType())
                        .addParameter(TypeName.get(field.type()), field.name());
                Optional<MethodDefinition> setter = setter(field);
                if (setter.isPresent()) {
                    builder
                            .addStatement("instance." + setter.get().name() + "($L)", field.name());
                } else if (field.isPrivate() || field.isProtected() && notWithinTheSamePackage()) {
                    builder
                            .addStatement("setField($S, $L)", field.name(), field.name())
                            .build();
                } else if (field.isPackageLocal()) {
                    if(!notWithinTheSamePackage())
                        builder
                                .addStatement("instance.$L = $L", field.name(), field.name())
                                .build();
                    else
                        builder
                                .addStatement("setField($S, $L)", field.name(), field.name())
                                .build();
                } else {
                    builder
                            .addStatement("instance.$L = $L", field.name(), field.name())
                            .build();
                }
                setters.add(builder
                        .addStatement("return this")
                        .build());
            }
        }
        return setters;
    }

    private Optional<MethodDefinition> setter(FieldDefinition field) {
        List<MethodDefinition> methods = source.methods(setterForField(field));
        if(methods.isEmpty()) return Optional.empty();
        return Optional.of(methods.get(0));
    }

    private Predicate<MethodDefinition> setterForField(final FieldDefinition field) {
        return new Predicate<MethodDefinition>() {
            @Override
            public boolean test(MethodDefinition methodDefinition) {
                String defaultSetter = Formatter.format("set$name",
                        field.name().substring(0, 1).toUpperCase() + field.name().substring(1));
                if(defaultSetter.equals(methodDefinition.name()))
                    return true;
                return false;
            }
        };
    }

    @Override
    protected List<FieldSpec> fields() {
        return Collections.emptyList();
    }

    @Override
    protected MethodSpec buildMethod() {
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(className(source))
                .addStatement(Formatter.format("$type result = instance;\n" +
                        "instance = newInstance();\n" +
                        "return result", className(source).toString()))
                .build();
    }

    @Override
    protected MethodSpec newInstance() {
        MethodSpec.Builder newInstance = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PROTECTED)
                .returns(className(source));
        Optional<ConstructorDefinition> defaultConstructor = defaultConstructor();
        String instantiationCode;;
        if (defaultConstructor.isPresent() && !defaultConstructor.get().isPrivate())
            newInstance.addStatement("return new $L()", className(source));
        else {
            if(defaultConstructor.isPresent() && defaultConstructor.get().isPrivate()) {
                instantiationCode = "\tjava.lang.reflect.Constructor<$L> constructor = $L.class.getDeclaredConstructor(new Class[0]);\n"  +
                        "constructor.setAccessible(true);\n" +
                        "instance = constructor.newInstance();\n" +
                "} catch (NoSuchMethodException e) {\n" +
                        "\tthrow new RuntimeException();\n";
            } else {
                instantiationCode = "\tinstance = ($L) $L.class.getDeclaredConstructors()[0].newInstance((Object[])new java.lang.reflect.Array[]{null});\n";
            }
            newInstance.addStatement("   $L instance;try {\n" +
                    instantiationCode +
                    "} catch (InstantiationException e) {\n" +
                    "\tthrow new RuntimeException();\n" +
                    "} catch (IllegalAccessException e) {\n" +
                    "\tthrow new RuntimeException();\n" +
                    "} catch (java.lang.reflect.InvocationTargetException e) {\n" +
                    "\tthrow new RuntimeException();\n" +
                    "}return instance", className(source), className(source), classNameWithoutGenerics(source));
        }
        return newInstance.build();
    }

    private Optional<ConstructorDefinition> defaultConstructor() {
        for (ConstructorDefinition constructor : source.constructors())
            if (constructor.parameters().isEmpty())
                return Optional.of(constructor);
        return Optional.empty();
    }
}
