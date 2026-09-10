package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class PrivateInheritedSetter extends Vehicle {
	private String color;

	public PrivateInheritedSetter() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
