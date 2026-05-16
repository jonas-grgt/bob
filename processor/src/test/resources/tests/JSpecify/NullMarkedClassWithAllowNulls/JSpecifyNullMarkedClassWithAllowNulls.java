package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Buildable(strategy = {Strategy.STRICT, Strategy.ALLOW_NULLS})
@NullMarked
public class JSpecifyNullMarkedClassWithAllowNulls {
	private String make;

	private String model;

	public JSpecifyNullMarkedClassWithAllowNulls(String make, @Nullable String model) {
		this.make = make;
		this.model = model;
	}
}
