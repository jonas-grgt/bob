package io.jonasg.bob.definitions;

import javax.lang.model.type.TypeMirror;
import java.util.List;

public class GenericParameterDefinition extends ParameterDefinition {

	private final List<SimpleTypeDefinition> bounds;

	public GenericParameterDefinition(TypeMirror type, String name, List<SimpleTypeDefinition> bounds) {
		super(type, name);
		this.bounds = bounds;
	}

	public List<SimpleTypeDefinition> bounds() {
		return bounds;
	}

}
