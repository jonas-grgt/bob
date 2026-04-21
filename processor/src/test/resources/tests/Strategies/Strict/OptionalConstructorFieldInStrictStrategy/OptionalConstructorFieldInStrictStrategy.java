package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class OptionalConstructorFieldInStrictStrategy {
	private String make;

	@Buildable.Optional
	private int year;

	public OptionalConstructorFieldInStrictStrategy(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
