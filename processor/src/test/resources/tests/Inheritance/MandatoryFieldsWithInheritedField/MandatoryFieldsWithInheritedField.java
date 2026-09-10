package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(mandatoryFields = "make")
public class MandatoryFieldsWithInheritedField extends Vehicle {
	private String color;

	public MandatoryFieldsWithInheritedField() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
