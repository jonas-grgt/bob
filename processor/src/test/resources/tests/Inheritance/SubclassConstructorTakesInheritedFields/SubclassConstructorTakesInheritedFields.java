package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class SubclassConstructorTakesInheritedFields extends Vehicle {
	private String color;

	public SubclassConstructorTakesInheritedFields(String make, String color) {
		super(make);
		this.color = color;
	}
}
