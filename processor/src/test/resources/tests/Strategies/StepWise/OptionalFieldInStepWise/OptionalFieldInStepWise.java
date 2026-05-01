package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Optional;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE)
public class OptionalFieldInStepWise {
	private String make;
	private int year;
	private String color;

	public OptionalFieldInStepWise(String make, @Optional int year, String color) {
		this.make = make;
		this.year = year;
		this.color = color;
	}
}
