package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE)
public class StepWiseInheritedFields extends Vehicle {
	private String color;

	public StepWiseInheritedFields(String make, String color) {
		super(make);
		this.color = color;
	}
}
