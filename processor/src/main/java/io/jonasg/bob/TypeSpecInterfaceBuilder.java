package io.jonasg.bob;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class TypeSpecInterfaceBuilder {

	public static FunctionalInterfaceBuilder functionalInterface(String name) {
		return new FunctionalInterfaceBuilder(name);
	}

	public static InterfaceBuilder anInterface(String name) {
		return new InterfaceBuilder(name);
	}

	public static class InterfaceBuilder {

		private final String name;

		private final List<MethodSpec> methods = new ArrayList<>();

		public InterfaceBuilder(String name) {
			this.name = name;
		}

		@SuppressWarnings("UnusedReturnValue")
		public InterfaceBuilder addMethod(MethodSpec methodSpec) {
			this.methods.add(methodSpec);
			return this;
		}

		public TypeSpec build() {
			var builder = TypeSpec.interfaceBuilder(this.name);
			builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
			this.methods.forEach(builder::addMethod);
			return builder.build();
		}
	}

	public static class FunctionalInterfaceBuilder {

		private final String name;

		private TypeName returnType;

		private final List<Argument> arguments = new ArrayList<>();

		private String methodName;

		public FunctionalInterfaceBuilder(String name) {
			this.name = name;
		}

		public FunctionalInterfaceBuilder methodName(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public FunctionalInterfaceBuilder returns(TypeName returnType) {
			this.returnType = returnType;
			return this;
		}

		public FunctionalInterfaceBuilder addArgument(TypeName type, String name) {
			this.arguments.add(new Argument(type, name));
			return this;
		}

		public TypeSpec build() {
			var builder = TypeSpec.interfaceBuilder(this.name);
			builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
			MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(this.methodName)
					.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
					.returns(this.returnType);
			this.arguments.forEach(a -> methodBuilder.addParameter(a.typeName, a.name));
			builder.addMethod(methodBuilder.build());
			return builder.build();

		}

		final class Argument {
			private final TypeName typeName;
			private final String name;

			Argument(TypeName typeName, String name) {
				this.typeName = typeName;
				this.name = name;
			}

			public TypeName typeName() {
				return typeName;
			}

			public String name() {
				return name;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == this)
					return true;
				if (obj == null || obj.getClass() != this.getClass())
					return false;
				var that = (Argument) obj;
				return Objects.equals(this.typeName, that.typeName) &&
						Objects.equals(this.name, that.name);
			}

			@Override
			public int hashCode() {
				return Objects.hash(typeName, name);
			}

			@Override
			public String toString() {
				return "Argument[" +
						"typeName=" + typeName + ", " +
						"name=" + name + ']';
			}

		}
	}
}
