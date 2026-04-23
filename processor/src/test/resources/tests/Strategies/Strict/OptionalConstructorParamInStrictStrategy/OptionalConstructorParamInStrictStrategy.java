package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class OptionalConstructorParamInStrictStrategy {
	private String make;

	private int year;

	public OptionalConstructorParamInStrictStrategy(String make, @Buildable.Optional int year) {
		this.make = make;
		this.year = year;
	}
}
