package io.jonasg.bob.definitions;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
				.genericParameters(generics(element))
				.packageName(packageName())
				.methods(methods())
				.enclosedIn(outerFullTypeName())
				.fields(fields())
				.constructors(constructors(element))
				.buildableTypeMirror(element.asType())
				.build();
	}

	private List<GenericParameterDefinition> generics(Element element) {
		List<GenericParameterDefinition> parameters = new ArrayList<>();
		if (ElementKind.CLASS.equals(element.getKind())) {
			for (TypeParameterElement param : ((TypeElement) element).getTypeParameters()) {
				parameters.add(new GenericParameterDefinition(param.asType(), param.getSimpleName().toString(),
						toTypeDefinitions(param.getBounds())));
			}
		}
		return parameters;
	}

	private List<SimpleTypeDefinition> toTypeDefinitions(List<? extends TypeMirror> mirrors) {
		List<SimpleTypeDefinition> definitions = new ArrayList<>();
		for (TypeMirror mirror : mirrors) {
			if (!"java.lang.Object".equals(mirror.toString())) {
				List<String> parts = new ArrayList<>(Arrays.asList(mirror.toString().split("\\.")));
				Collections.reverse(parts);
				String name = parts.get(0);
				parts.remove(0);
				Collections.reverse(parts);
				String packageName = join(parts.toArray(new String[0]), ".");
				definitions.add(new SimpleTypeDefinition(name, packageName));
			}
		}
		return definitions;
	}

	private static String join(String[] aArr, String sSep) {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0) {
				sbStr.append(sSep);
			}
			sbStr.append(aArr[i]);
		}
		return sbStr.toString();
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
					constructor.getAnnotationMirrors()));
		}
		return definitions;
	}

	private String outerType(Element enclosingElement) {
		StringBuilder enclosedIn = null;
		while (!enclosingElement.getKind().equals(ElementKind.PACKAGE)) {
			if (enclosedIn == null) {
				enclosedIn = Optional.ofNullable(enclosingElement.getSimpleName().toString())
						.map(StringBuilder::new)
						.orElse(null);
			} else {
				enclosedIn.append(String.format(".%s", enclosingElement.getSimpleName()));
			}
			enclosingElement = enclosingElement.getEnclosingElement();
		}
		return enclosedIn == null ? null : enclosedIn.toString();
	}

	private String typeName() {
		return element.getSimpleName().toString();
	}

	private String packageName() {
		return elementUtils.getPackageOf(element).getQualifiedName().toString();
	}

	private String outerFullTypeName() {
		Element enclosingElement = element.getEnclosingElement();
		return outerType(enclosingElement);
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
				.map(this::toMethodDefinition)
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
				if (method.getModifiers().contains(Modifier.PRIVATE)
						|| method.getModifiers().contains(Modifier.STATIC)) {
					continue;
				}
				if (!method.getModifiers().contains(Modifier.PUBLIC)
						&& !isInSamePackage(superclassElement)) {
					continue;
				}
				definitions.add(toMethodDefinition(method));
			}
			superclass = superclassElement.getSuperclass();
		}
		return definitions;
	}

	private boolean isInSamePackage(TypeElement other) {
		return elementUtils.getPackageOf(other).getQualifiedName()
				.equals(elementUtils.getPackageOf(element).getQualifiedName());
	}

	private MethodDefinition toMethodDefinition(ExecutableElement method) {
		return new MethodDefinition(method.getSimpleName().toString(), parameterTypes(method));
	}

	private List<TypeMirror> parameterTypes(ExecutableElement element) {
		return element.getParameters().stream()
				.map(VariableElement::asType)
				.collect(Collectors.toList());
	}
}
