package io.jonasg.bob;

import com.palantir.javapoet.TypeSpec;
import io.jonasg.bob.definitions.TypeDefinition;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Map;

class BuilderGenerator {

	private final Filer filer;

	private final Messager messager;

	private final Map<TypeMirror, DefaultValues> defaultsForBuildable;

	public BuilderGenerator(Filer filer, Messager messager, Map<TypeMirror, DefaultValues> defaultsForBuildable) {
		this.filer = filer;
		this.messager = messager;
		this.defaultsForBuildable = defaultsForBuildable;
	}

	public void generate(TypeDefinition typeDefinition,
			Buildable buildable,
			Types typeUtils,
			boolean isInNullMarkedScope,
			Element buildableElement) {
		String packageName = getPackageName(typeDefinition, buildable);
		var builderTypeSpecFactory = new BuilderTypeSpecFactory(
				typeDefinition,
				buildable,
				typeUtils,
				packageName,
				isInNullMarkedScope,
				messager,
				buildableElement);

		var defaults = this.defaultsForBuildable.get(typeDefinition.buildableTypeMirror());
		if (defaults != null) {
			builderTypeSpecFactory.setDefaults(defaults);
		}

		List<TypeSpec> typeSpecs = builderTypeSpecFactory.typeSpecs();
		typeSpecs.forEach(t -> TypeWriter.write(filer, packageName, t));
	}

	private String getPackageName(TypeDefinition typeDefinition, Buildable buildable) {
		if (!buildable.packageName().isEmpty()) {
			return buildable.packageName();
		}
		return typeDefinition.packageName();
	}

}
