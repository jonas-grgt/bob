package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

import java.util.UUID;
import java.util.function.Supplier;

@Buildable(strategy = Strategy.STRICT)
public record WithSupplierDefault(
		String uniqueName,
		String other
) {

	@Buildable.Defaults
	public static class Defaults {
		public static Supplier<String> uniqueName = () -> "Name: " + UUID.randomUUID();
	}
}
