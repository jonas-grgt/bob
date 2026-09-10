package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class SuperclassDefaultsNotApplied extends Vehicle {
	private String color;

	public SuperclassDefaultsNotApplied() {
	}

	public void setColor(String color) {
		this.color = color;
	}
}
