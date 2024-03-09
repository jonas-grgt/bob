package io.jonasg.bob.specs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import io.jonasg.bob.AbstractBuilder;
import io.jonasg.bob.Buildable;
import io.jonasg.bob.definitions.ConstructorDefinition;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.GenericParameterDefinition;
import io.jonasg.bob.definitions.ParameterDefinition;
import io.jonasg.bob.definitions.SimpleTypeDefinition;
import io.jonasg.bob.definitions.TypeDefinition;
import io.jonasg.bob.utils.Formatter;

abstract class BaseTypeSpecFactory {

    protected TypeDefinition source;
    protected Buildable buildable;

    protected BaseTypeSpecFactory(TypeDefinition source, Buildable buildable) {
        this.source = source;
        this.buildable = buildable;
    }

    protected String builderTypeName(TypeDefinition source) {
        return Formatter.format("$typeName$suffix", source.typeName(), "Builder");
    }

	protected abstract MethodSpec newInstance();

	/**
	 * The setters that the builder will have
	 * @return List of {@link MethodSpec}
	 */
	protected abstract List<MethodSpec> setters();

	/**
	 * The fields that the builder will have
	 * @return List of {@link FieldSpec}
	 */
	protected abstract List<FieldSpec> fields();


	/**
	 * The build method that the builder will have
	 * @return {@link MethodSpec}
	 */
	protected abstract MethodSpec buildMethod();

    protected TypeSpec typeSpec() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(builderTypeName(source))
                .superclass(ParameterizedTypeName.get(ClassName.get(AbstractBuilder.class), className(source)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        if (!source.genericParameters().isEmpty())
            builder.addTypeVariables(toTypeVariableNames(source));
        builder.addMethod(newInstance());
        builder.addMethods(setters());
        builder.addFields(fields());
        builder.addMethod(buildMethod());
        builder.addMethod(constructor());
        if (!source.genericParameters().isEmpty())
            builder.addMethod(of());
        return builder.build();
    }

    private MethodSpec of() {
        CodeBlock.Builder body = CodeBlock.builder();
        body.addStatement("return new $L<>()", builderTypeName(source));
        MethodSpec.Builder of = MethodSpec.methodBuilder("of")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariables(builderTypeGenerics())
                .returns(builderType());
        for (ParameterDefinition parameter : source.genericParameters())
            of.addParameter(ParameterizedTypeName.get(ClassName.get("java.lang", "Class"), TypeVariableName.get(parameter.name())), String.format("%stype", parameter.name()));
        of.addCode(body.build());
        return of.build();
    }

    protected TypeName builderType() {
        if (source.genericParameters().isEmpty())
            return ClassName.get(builderPackage(source, buildable), builderTypeName(source));
        List<TypeVariableName> typeVariableNames = toTypeVariableNames(source);
        return ParameterizedTypeName.get(ClassName.get(builderPackage(source, buildable), builderTypeName(source)), typeVariableNames.toArray(new TypeName[typeVariableNames.size()]));
    }

    public static String builderPackage(TypeDefinition source, Buildable buildable) {
        if (!buildable.packageName().isEmpty())
            return buildable.packageName();
        else
            return String.format("%s.builder", source.packageName());
    }

    private List<TypeVariableName> builderTypeGenerics() {
        List<TypeVariableName> typeVariableNames = new ArrayList<>();
        for (GenericParameterDefinition param : source.genericParameters()) {
            List<TypeVariableName> bounds = new ArrayList<>();
            for (SimpleTypeDefinition definition : param.bounds()) {
                bounds.add(TypeVariableName.get(definition.typeName()));
            }
            typeVariableNames.add(TypeVariableName.get(param.name(), bounds.toArray(new TypeName[bounds.size()])));
        }
        return typeVariableNames;
    }

    protected TypeName className(TypeDefinition definition) {
        if (definition.genericParameters().isEmpty()) {
            if (definition.isNested())
                return ClassName.get(definition.packageName(), definition.nestedIn()).nestedClass(definition.typeName());
            else
                return ClassName.get(definition.packageName(), definition.fullTypeName());
        } else {
            List<TypeVariableName> genericParameters = toTypeVariableNames(definition);
            return ParameterizedTypeName.get(ClassName.get(definition.packageName(), definition.fullTypeName()),
                    genericParameters.toArray(new TypeName[genericParameters.size()]));
        }
    }

    protected List<TypeVariableName> toTypeVariableNames(TypeDefinition definition) {
        List<TypeVariableName> genericParameters = new ArrayList<>();
        for (GenericParameterDefinition parameterDefinition : definition.genericParameters())
            genericParameters.add(TypeVariableName.get(parameterDefinition.name(), simpleClassNames(parameterDefinition.bounds()).toArray(new TypeName[parameterDefinition.bounds().size()])));
        return genericParameters;
    }

    private List<TypeName> simpleClassNames(List<SimpleTypeDefinition> definitions) {
        List<TypeName> typeNames = new ArrayList<>();
        for (SimpleTypeDefinition definition : definitions)
            typeNames.add(ClassName.get(definition.packageName(), definition.fullTypeName()));
        return typeNames;
    }

    private TypeName classNameWithoutGenerics(TypeDefinition definition) {
        return ClassName.get(definition.packageName(), definition.fullTypeName());
    }

    private boolean defaultConstructorPresent() {
        if (source.constructors().isEmpty())
            return true;
        else
            for (ConstructorDefinition constructor : source.constructors())
                if (constructor.parameters().isEmpty())
                    return true;
        return false;
    }

    protected String fieldName(String name) {
        if(buildable.prefix().isEmpty())
            return name;
        return Formatter.format("$prefix$name", buildable.prefix(), name.substring(0,1).toUpperCase() + name.substring(1));
    }

    protected boolean notExcluded(FieldDefinition field) {
        return !Arrays.asList(buildable.excludes()).contains(field.name());
    }

    protected boolean notWithinTheSamePackage() {
        return !source.packageName().equals(buildable.packageName());
    }

    protected MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
    }
}
