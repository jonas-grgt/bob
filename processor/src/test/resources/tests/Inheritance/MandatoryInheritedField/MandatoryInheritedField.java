package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class MandatoryInheritedField extends Vehicle {
	private String color;

	public MandatoryInheritedField(String make, String color) {
		super(make);
		this.color = color;
	}
}
