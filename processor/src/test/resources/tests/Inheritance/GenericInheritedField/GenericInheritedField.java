package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class GenericInheritedField extends Animal<String> {
	private String color;

	public GenericInheritedField(String name, String color) {
		super(name);
		this.color = color;
	}
}
