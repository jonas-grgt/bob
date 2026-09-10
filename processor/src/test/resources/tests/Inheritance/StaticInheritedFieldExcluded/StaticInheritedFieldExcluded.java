package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class StaticInheritedFieldExcluded extends Vehicle {
	private String color;

	public StaticInheritedFieldExcluded(String make, String color) {
		super(make);
		this.color = color;
	}
}
