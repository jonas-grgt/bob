package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeKind;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines a specific Java Type.
 * It declares its fields, constructors, methods and generic parameters.
 */
public class TypeDefinition extends SimpleTypeDefinition {

	private List<FieldDefinition> fields = new ArrayList<>();

	private List<ConstructorDefinition> constructors = new ArrayList<>();

	private List<GenericParameterDefinition> genericParameters = new ArrayList<>();

	private List<MethodDefinition> methods;

	public TypeDefinition() {
		super();
	}

	public List<FieldDefinition> fields() {
		return fields;
	}

	public String nestedIn() {
		return parent;
	}

	public List<GenericParameterDefinition> genericParameters() {
		return new ArrayList<>(genericParameters);
	}

	public List<ConstructorDefinition> constructors() {
		return new ArrayList<>(constructors);
	}

	public String fullTypeName() {
		return (parent != null ? parent + "." : "") + typeName;
	}

	public boolean isNested() {
		return parent != null;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public List<SetterMethodDefinition> getSetterMethods() {
		List<SetterMethodDefinition> setters = new ArrayList<>();
		List<MethodDefinition> methodsWithOneParam = this.methods.stream()
				.filter(m -> m.parameters().size() == 1)
				.collect(Collectors.toList());
		for (FieldDefinition field : fields) {
			String name = field.name().substring(0, 1).toUpperCase() + field.name().substring(1);
			methodsWithOneParam.stream()
					.filter(m -> m.name().equals(field.name()) ||
							isABooleanField(field, m))
					.findFirst()
					.map(m -> new SetterMethodDefinition(m.name(), field, m.parameters().get(0)))
					.ifPresent(setters::add);
			methodsWithOneParam.stream()
					.filter(m -> m.name().equals(String.format("set%s", name))
							|| (m.name().equals(String.format("set%s", name.substring(2)))
									&& field.type().getKind().equals(TypeKind.BOOLEAN)))
					.findFirst()
					.map(m -> new SetterMethodDefinition(m.name(), field, m.parameters().get(0)))
					.ifPresent(setters::add);
		}
		return setters;
	}

	private boolean isABooleanField(FieldDefinition field, MethodDefinition m) {
		return field.name().startsWith("is")
				&& m.name().equalsIgnoreCase(field.name().substring(2))
				&& field.type().getKind().equals(TypeKind.BOOLEAN);
	}

	public boolean containsSetterMethods() {
		return !getSetterMethods().isEmpty();
	}

	public static class Builder {
		private TypeDefinition instance = new TypeDefinition();

		public Builder typeName(String typeName) {
			instance.typeName = typeName;
			return this;
		}

		public Builder packageName(String packageName) {
			instance.packageName = packageName;
			return this;
		}

		public Builder methods(List<MethodDefinition> methods) {
			instance.methods = methods;
			return this;
		}

		public Builder enclosedIn(String enclosedIn) {
			instance.parent = enclosedIn;
			return this;
		}

		public Builder fields(List<FieldDefinition> fields) {
			instance.fields = fields;
			return this;
		}

		public Builder constructors(List<ConstructorDefinition> constructors) {
			instance.constructors = constructors;
			return this;
		}

		public Builder genericParameters(List<GenericParameterDefinition> generics) {
			instance.genericParameters = generics;
			return this;
		}

		public TypeDefinition build() {
			TypeDefinition result = instance;
			instance = null;
			return result;
		}
	}
}
