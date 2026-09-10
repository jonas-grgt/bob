package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class StrictInheritedFields extends Vehicle {
	private String color;

	public StrictInheritedFields(String make, String color) {
		super(make);
		this.color = color;
	}
}
