package io.jonasg.bob.specs;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.definitions.ConstructorDefinition;
import io.jonasg.bob.definitions.FieldDefinition;
import io.jonasg.bob.definitions.ParameterDefinition;
import io.jonasg.bob.definitions.TypeDefinition;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jonasg.bob.utils.Formatter.format;

public class FieldsInsideBuilderTypeSpecFactory extends BaseTypeSpecFactory {

	protected FieldsInsideBuilderTypeSpecFactory(TypeDefinition source, Buildable buildable) {
		super(source, buildable);
	}

	@Override
	protected MethodSpec newInstance() {
		Optional<ConstructorDefinition> constructor = constructorMatchingFinalFields();
		if (!constructor.isPresent())
			throw new IllegalStateException("Missing constructor for final fields");
		return MethodSpec.methodBuilder("newInstance")
				.addModifiers(Modifier.PROTECTED)
				.returns(className(source))
				.addStatement("return new $L(" + constructorArguments(constructor.get().parameters()) + ")", args(constructor.get().parameters()))
				.build();
	}

	private Object[] args(List<ParameterDefinition> constructorParams) {
		final List<String> finalFields = finalFields();
		List<String> parameters = constructorParams.stream()
				.map(p -> finalFields.contains(p.name()) ? p.name() : "null")
				.toList();
		Object[] args = new Object[constructorParams.size() + 1];
		args[0] = className(source);
		for (int i = 1; i <= parameters.size(); i++) {
			args[i] = parameters.get(i - 1);
		}
		return args;
	}

	private List<String> finalFields() {
		return source.fields(FieldDefinition::isFinal).stream()
				.map(FieldDefinition::name)
				.toList();
	}

	private String constructorArguments(List<ParameterDefinition> params) {
		return params.stream()
				.map(parameterDefinition -> "$L")
				.collect(Collectors.joining(","));
	}

	private Optional<ConstructorDefinition> constructorMatchingFinalFields() {
		List<FieldDefinition> finalFields = source.fields(new Predicate<FieldDefinition>() {
			@Override
			public boolean test(FieldDefinition fieldDefinition) {
				return fieldDefinition.isFinal();
			}
		});
		for (ConstructorDefinition constructor : source.constructors()) {
			for (FieldDefinition finalField : finalFields) {
				if (!constructor.parameters().contains(finalField.name()))
					break;
			}
			return Optional.of(constructor);
		}
		return Optional.empty();
	}

	@Override
	protected List<MethodSpec> setters() {
		List<MethodSpec> setters = new ArrayList<>();
		for (FieldDefinition field : source.fields()) {
			if (notExcluded(field)) {
				MethodSpec.Builder setter = MethodSpec.methodBuilder(fieldName(field.name()))
						.addModifiers(Modifier.PUBLIC)
						.returns(builderType())
						.addParameter(TypeName.get(field.type()), field.name());
				setter
						.addStatement("this.$L = $L", field.name(), field.name())
						.build();
				setters.add(setter
						.addStatement("return this")
						.build());
			}
		}
		return setters;
	}

	@Override
	protected List<FieldSpec> fields() {
		return source.fields().stream()
				.map(field -> FieldSpec.builder(TypeName.get(field.type()), field.name(), Modifier.PRIVATE)
						.build())
				.toList();
	}

	@Override
	protected MethodSpec buildMethod() {
		final MethodSpec.Builder builder = MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PUBLIC)
				.returns(className(source))
				.addStatement(format("$type result = newInstance()", className(source).toString()));
		source.fields().stream()
				.filter(field -> !field.isFinal())
				.forEach(field -> {
					if (field.isPublic()) {
						builder.addStatement(format("result.$fieldName = $fieldName", field.name(), field.name()));
					} else if (field.isPrivate()) {
						builder.addStatement(format("setField(result, \"$fieldName\",  $fieldName)", field.name(), field.name()));
					} else if (field.isProtected() && notWithinTheSamePackage()) {
						builder.addStatement(format("setField(result, \"$fieldName\",  $fieldName)", field.name(), field.name()));
					} else {
						builder.addStatement(format("result.$fieldName = $fieldName", field.name(), field.name()));
					}
				});
		builder.addStatement("return result", className(source).toString());
		return builder.build();
	}

	public static TypeSpec produce(TypeDefinition source, Buildable buildable) {
		return new FieldsInsideBuilderTypeSpecFactory(source, buildable).typeSpec();
	}
}
