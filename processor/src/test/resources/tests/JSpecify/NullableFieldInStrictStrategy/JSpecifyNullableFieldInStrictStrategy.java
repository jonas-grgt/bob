package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import org.jspecify.annotations.Nullable;

@Buildable(strategy = Strategy.STRICT)
public class JSpecifyNullableFieldInStrictStrategy {
	private String make;

	private String model;

	public JSpecifyNullableFieldInStrictStrategy(String make, @Nullable String model) {
		this.make = make;
		this.model = model;
	}
}
