package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class InheritedFieldWithDefaults extends Vehicle {
	private String color;

	public InheritedFieldWithDefaults() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
