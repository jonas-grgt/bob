package io.jonasg.bob;

import com.palantir.javapoet.TypeSpec;
import io.jonasg.bob.definitions.TypeDefinition;

import javax.annotation.processing.Filer;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Map;

class BuilderGenerator {

	private static final String GENERATED_BY = BuildableProcessor.class.getCanonicalName();

	private final Filer filer;

	private final Map<TypeMirror, DefaultValues> defaultsForBuildable;

	public BuilderGenerator(Filer filer, Map<TypeMirror, DefaultValues> defaultsForBuildable) {
		this.filer = filer;
		this.defaultsForBuildable = defaultsForBuildable;
	}

	public void generate(TypeDefinition typeDefinition,
			Buildable buildable,
			Types typeUtils,
			boolean isInNullMarkedScope) {
		String packageName = getPackageName(typeDefinition, buildable);
		var builderTypeSpecFactory = new BuilderTypeSpecFactory(
				typeDefinition,
				buildable,
				typeUtils,
				packageName,
				isInNullMarkedScope);

		var defaults = this.defaultsForBuildable.get(typeDefinition.buildableTypeMirror());
		if (defaults != null) {
			builderTypeSpecFactory.setDefaults(defaults);
		}

		List<TypeSpec> typeSpecs = builderTypeSpecFactory.typeSpecs();
		typeSpecs.forEach(t -> TypeWriter.write(filer, packageName, t, GENERATED_BY));
	}

	private String getPackageName(TypeDefinition typeDefinition, Buildable buildable) {
		if (!buildable.packageName().isEmpty()) {
			return buildable.packageName();
		}
		return typeDefinition.packageName();
	}

}
