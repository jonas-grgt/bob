package io.jonasg.bob.definitions;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeVariableName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TypeDefinitionFactory {

	protected final Elements elementUtils;

	private final Types types;

	private Element element;

	public TypeDefinitionFactory(Elements elementUtils, Types types) {
		this.elementUtils = elementUtils;
		this.types = types;
	}

	/**
	 * Create a TypeDefinition for the given element
	 *
	 * @param element
	 *            the element to create a TypeDefinition for
	 * @return a TypeDefinition for the given element
	 */
	public TypeDefinition typeDefinitionForElement(Element element) {
		this.element = element;
		return TypeDefinition.newBuilder()
				.typeName(typeName())
				.typeVariables(((TypeElement) element).getTypeParameters().stream()
						.map(TypeVariableName::get)
						.toList())
				.className(ClassName.get((TypeElement) element))
				.packageName(packageName())
				.methods(methods())
				.fields(fields())
				.constructors(constructors(element))
				.types(types)
				.build();
	}

	private List<ConstructorDefinition> constructors(Element element) {
		List<ConstructorDefinition> definitions = new ArrayList<>();
		for (ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
			List<ParameterDefinition> constructorParams = new ArrayList<>();
			for (VariableElement param : constructor.getParameters()) {
				var allAnnotations = new ArrayList<AnnotationMirror>();
				allAnnotations.addAll(param.getAnnotationMirrors());
				allAnnotations.addAll(param.asType().getAnnotationMirrors());
				constructorParams.add(new ParameterDefinition(param.asType(), param.getSimpleName().toString(),
						allAnnotations));
			}
			definitions.add(new ConstructorDefinition(constructorParams, constructor.getModifiers(),
					constructor.getAnnotationMirrors(), packageName()));
		}
		return definitions;
	}

	private String typeName() {
		return element.getSimpleName().toString();
	}

	private String packageName() {
		return elementUtils.getPackageOf(element).getQualifiedName().toString();
	}

	private List<FieldDefinition> fields() {
		List<FieldDefinition> definitions = new ArrayList<>();
		Set<String> seenFieldNames = new HashSet<>();
		for (VariableElement field : ElementFilter.fieldsIn(element.getEnclosedElements())) {
			definitions.add(toFieldDefinition(field, field.asType()));
			seenFieldNames.add(field.getSimpleName().toString());
		}
		definitions.addAll(inheritedFields(seenFieldNames));
		return definitions;
	}

	private List<FieldDefinition> inheritedFields(Set<String> seenFieldNames) {
		List<FieldDefinition> definitions = new ArrayList<>();
		TypeMirror superclass = ((TypeElement) element).getSuperclass();
		while (superclass.getKind() != TypeKind.NONE) {
			DeclaredType declaredSuperclass = (DeclaredType) superclass;
			TypeElement superclassElement = (TypeElement) declaredSuperclass.asElement();
			for (VariableElement field : ElementFilter.fieldsIn(superclassElement.getEnclosedElements())) {
				String fieldName = field.getSimpleName().toString();
				if (seenFieldNames.contains(fieldName) || field.getModifiers().contains(Modifier.STATIC)) {
					continue;
				}
				definitions.add(toFieldDefinition(field, types.asMemberOf(declaredSuperclass, field)));
				seenFieldNames.add(fieldName);
			}
			superclass = superclassElement.getSuperclass();
		}
		return definitions;
	}

	private FieldDefinition toFieldDefinition(VariableElement field, TypeMirror type) {
		var allAnnotations = new ArrayList<AnnotationMirror>();
		allAnnotations.addAll(field.getAnnotationMirrors());
		allAnnotations.addAll(type.getAnnotationMirrors());
		return new FieldDefinition(field.getSimpleName().toString(), allAnnotations, type);
	}

	private List<MethodDefinition> methods() {
		List<MethodDefinition> definitions = ElementFilter.methodsIn(element.getEnclosedElements()).stream()
				.map(m -> toMethodDefinition(m, parameterTypes(m)))
				.collect(Collectors.toList());
		definitions.addAll(inheritedMethods());
		return definitions;
	}

	private List<MethodDefinition> inheritedMethods() {
		List<MethodDefinition> definitions = new ArrayList<>();
		TypeMirror superclass = ((TypeElement) element).getSuperclass();
		while (superclass.getKind() != TypeKind.NONE) {
			DeclaredType declaredSuperclass = (DeclaredType) superclass;
			TypeElement superclassElement = (TypeElement) declaredSuperclass.asElement();
			for (ExecutableElement method : ElementFilter.methodsIn(superclassElement.getEnclosedElements())) {
				if (method.getModifiers().contains(Modifier.STATIC)) {
					continue;
				}
				// resolve type variables of a generic superclass, like inherited fields
				var methodType = (ExecutableType) types.asMemberOf(declaredSuperclass, method);
				definitions.add(toMethodDefinition(method, List.copyOf(methodType.getParameterTypes())));
			}
			superclass = superclassElement.getSuperclass();
		}
		return definitions;
	}

	private MethodDefinition toMethodDefinition(ExecutableElement method, List<TypeMirror> parameterTypes) {
		return new MethodDefinition(method.getSimpleName().toString(), parameterTypes,
				method.getModifiers(),
				elementUtils.getPackageOf(method).getQualifiedName().toString());
	}

	private List<TypeMirror> parameterTypes(ExecutableElement element) {
		return element.getParameters().stream()
				.map(VariableElement::asType)
				.collect(Collectors.toList());
	}
}
