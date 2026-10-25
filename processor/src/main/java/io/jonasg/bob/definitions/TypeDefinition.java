package io.jonasg.bob.definitions;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeVariableName;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Defines a specific Java Type.
 * It declares its fields, constructors, methods and generic parameters.
 */
public class TypeDefinition {

	private String typeName;

	private String packageName;

	private ClassName className;

	private List<FieldDefinition> fields = new ArrayList<>();

	private List<ConstructorDefinition> constructors = new ArrayList<>();

	private List<TypeVariableName> typeVariables = new ArrayList<>();

	private Types types;

	private List<MethodDefinition> methods;

	private TypeMirror buildableTypeMirror;

	public String typeName() {
		return typeName;
	}

	public String packageName() {
		return packageName;
	}

	/**
	 * The raw (non-parameterized) class name, including any enclosing types.
	 */
	public ClassName className() {
		return className;
	}

	/**
	 * {@code raw} parameterized with this type's type variables, e.g.
	 * {@code Box<T>}.
	 */
	public TypeName withTypeVariables(ClassName raw) {
		if (typeVariables.isEmpty()) {
			return raw;
		}
		return ParameterizedTypeName.get(raw, typeVariables.toArray(new TypeName[0]));
	}

	public List<FieldDefinition> fields() {
		return fields;
	}

	/**
	 * The declared type variables, including their bounds.
	 */
	public List<TypeVariableName> typeVariables() {
		return new ArrayList<>(typeVariables);
	}

	public List<ConstructorDefinition> constructors() {
		return new ArrayList<>(constructors);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * Setter methods that can be called from a type in the given package, at most
	 * one
	 * per field. Fluent {@code x(..)} and {@code setX(..)} methods both qualify;
	 * one
	 * whose parameter type matches the field type is preferred.
	 */
	public List<SetterMethodDefinition> getSetterMethods(String accessibleFromPackage) {
		List<SetterMethodDefinition> setters = new ArrayList<>();
		List<MethodDefinition> methodsWithOneParam = this.methods.stream()
				.filter(m -> m.parameters().size() == 1)
				.filter(m -> m.isAccessibleFrom(accessibleFromPackage))
				.toList();
		for (FieldDefinition field : fields) {
			List<MethodDefinition> candidates = methodsWithOneParam.stream()
					.filter(m -> isFluentSetter(field, m) || isPrefixedSetter(field, m))
					.sorted(Comparator.comparing((MethodDefinition m) -> !isFluentSetter(field, m)))
					.toList();
			candidates.stream()
					.filter(m -> types.isSameType(m.parameters().get(0), field.type()))
					.findFirst()
					.or(() -> candidates.stream().findFirst())
					.map(m -> new SetterMethodDefinition(m.name(), field, m.parameters().get(0)))
					.ifPresent(setters::add);
		}
		return setters;
	}

	private boolean isFluentSetter(FieldDefinition field, MethodDefinition m) {
		return m.name().equals(field.name()) || isABooleanField(field, m);
	}

	private boolean isPrefixedSetter(FieldDefinition field, MethodDefinition m) {
		String name = field.name().substring(0, 1).toUpperCase() + field.name().substring(1);
		return m.name().equals(String.format("set%s", name))
				|| (isIsPrefixedBooleanField(field)
						&& m.name().equals(String.format("set%s", name.substring(2))));
	}

	private boolean isABooleanField(FieldDefinition field, MethodDefinition m) {
		return isIsPrefixedBooleanField(field)
				&& m.name().equalsIgnoreCase(field.name().substring(2));
	}

	private boolean isIsPrefixedBooleanField(FieldDefinition field) {
		return field.name().startsWith("is")
				&& field.type().getKind().equals(TypeKind.BOOLEAN);
	}

	public boolean containsSetterMethods(String accessibleFromPackage) {
		return !getSetterMethods(accessibleFromPackage).isEmpty();
	}

	/**
	 * The TypeMirror of the class that is Buildable
	 */
	public TypeMirror buildableTypeMirror() {
		return buildableTypeMirror;
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

		public Builder className(ClassName className) {
			instance.className = className;
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

		public Builder typeVariables(List<TypeVariableName> typeVariables) {
			instance.typeVariables = typeVariables;
			return this;
		}

		public Builder types(Types types) {
			instance.types = types;
			return this;
		}

		public Builder buildableTypeMirror(TypeMirror type) {
			instance.buildableTypeMirror = type;
			return this;
		}

		public TypeDefinition build() {
			TypeDefinition result = instance;
			instance = null;
			return result;
		}
	}
}
