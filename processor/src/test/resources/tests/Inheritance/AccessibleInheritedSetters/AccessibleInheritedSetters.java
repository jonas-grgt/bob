package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class AccessibleInheritedSetters extends Vehicle {
	private String color;

	public AccessibleInheritedSetters() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
