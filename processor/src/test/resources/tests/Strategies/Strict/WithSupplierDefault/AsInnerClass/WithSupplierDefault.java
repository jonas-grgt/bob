package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

import java.util.UUID;
import java.util.function.Supplier;

@Buildable(strategy = Strategy.STRICT)
public class WithSupplierDefault {
	private String uniqueName;

	private String other;

	public WithSupplierDefault(String uniqueName, String other) {
		this.uniqueName = uniqueName;
		this.other = other;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static Supplier<String> uniqueName = () -> "Name: " + UUID.randomUUID();
	}
}
