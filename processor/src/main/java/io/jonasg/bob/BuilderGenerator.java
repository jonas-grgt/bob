package io.jonasg.bob;

import javax.annotation.processing.Filer;
import javax.lang.model.util.Types;

import com.squareup.javapoet.TypeSpec;
import io.jonasg.bob.definitions.TypeDefinition;

import java.util.List;

public class BuilderGenerator {

	private final Filer filer;

	public BuilderGenerator(Filer filer) {
		this.filer = filer;
	}

	public void generate(TypeDefinition typeDefinition, Buildable buildable, Types typeUtils) {
		String packageName = getPackageName(typeDefinition, buildable);
		var abstractTypeSpecFactory = new BuilderTypeSpecFactory(typeDefinition, buildable, typeUtils, packageName);
		List<TypeSpec> typeSpecs = abstractTypeSpecFactory.typeSpecs();
		typeSpecs.forEach(t -> TypeWriter.write(filer, packageName, t));
	}

	private String getPackageName(TypeDefinition typeDefinition, Buildable buildable) {

		if (!buildable.packageName().isEmpty()) {
			return buildable.packageName();
		}
		return typeDefinition.packageName();
	}

}
