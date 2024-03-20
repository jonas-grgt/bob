package io.jonasg.bob.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

public class TypeDefinitionFactory {

	protected final Elements elementUtils;

	private Element element;

	public TypeDefinitionFactory(Elements elementUtils) {
		this.elementUtils = elementUtils;
	}

	/**
	 * Create a TypeDefinition for the given element
	 *
	 * @param element the element to create a TypeDefinition for
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
				.build();
	}

	private List<GenericParameterDefinition> generics(Element element) {
		List<GenericParameterDefinition> parameters = new ArrayList<>();
		if (ElementKind.CLASS.equals(element.getKind())) {
			for (TypeParameterElement param : ((TypeElement) element).getTypeParameters()) {
				parameters.add(new GenericParameterDefinition(param.asType(), param.getSimpleName().toString(), toTypeDefinitions(param.getBounds())));
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
				String packageName = join(parts.toArray(new String[parts.size()]), ".");
				definitions.add(new SimpleTypeDefinition(name, packageName));
			}
		}
		return definitions;
	}

	private static String join(String[] aArr, String sSep) {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0)
				sbStr.append(sSep);
			sbStr.append(aArr[i]);
		}
		return sbStr.toString();
	}

	private List<FieldDefinition> fields(List<VariableElement> fields) {
		List<FieldDefinition> definitions = new ArrayList<>();
		for (VariableElement field : fields)
			definitions.add(new FieldDefinition(field.getSimpleName().toString(), field.getModifiers(), field.asType()));
		return definitions;
	}

	private List<ConstructorDefinition> constructors(Element element) {
		List<ConstructorDefinition> definitions = new ArrayList<>();
		for (ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
			List<ParameterDefinition> constructorParams = new ArrayList<>();
			for (VariableElement param : constructor.getParameters()) {
				constructorParams.add(new ParameterDefinition(param.asType(), param.getSimpleName().toString()));
			}
			definitions.add(new ConstructorDefinition(constructorParams, constructor.getModifiers(), constructor.getAnnotationMirrors()));
		}
		return definitions;
	}

	private String outerType(Element enclosingElement) {
		String enclosedIn = null;
		while (!enclosingElement.getKind().equals(ElementKind.PACKAGE)) {
			if (enclosedIn == null)
				enclosedIn = enclosingElement.getSimpleName().toString();
			else
				enclosedIn += String.format(".%s", enclosingElement.getSimpleName());
			enclosingElement = enclosingElement.getEnclosingElement();
		}
		return enclosedIn;
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
		return fields(ElementFilter.fieldsIn(element.getEnclosedElements()));
	}

	private List<MethodDefinition> methods() {
		return ElementFilter.methodsIn(element.getEnclosedElements()).stream()
				.map(e -> new MethodDefinition(e.getSimpleName().toString()))
				.toList();
	}
}
