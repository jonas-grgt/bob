package io.jonasg.bob;

import javax.annotation.processing.Filer;
import javax.lang.model.util.Types;

import com.squareup.javapoet.TypeSpec;
import io.jonasg.bob.definitions.TypeDefinition;

public class BuilderGenerator {

	private final Filer filer;

	public BuilderGenerator(Filer filer) {
		this.filer = filer;
	}

	public void generate(TypeDefinition typeDefinition, Buildable buildable, Types typeUtils) {
		var abstractTypeSpecFactory = new BuilderTypeSpecFactory(typeDefinition, buildable, typeUtils);
		TypeSpec typeSpec = abstractTypeSpecFactory.typeSpec();
		String result;
		if (!buildable.packageName().isEmpty()) {
			result = buildable.packageName();
		} else {
			result = String.format("%s.builder", typeDefinition.packageName());
		}
		TypeWriter.write(filer, result, typeSpec);
	}

}
