package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class InheritedSetters extends Vehicle {
	private String color;

	public InheritedSetters() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
