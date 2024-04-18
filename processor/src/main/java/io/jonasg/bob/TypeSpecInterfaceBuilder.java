package io.jonasg.bob;

import java.util.ArrayList;
import java.util.List;

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

		record Argument(TypeName typeName, String name) {
		}
	}
}
