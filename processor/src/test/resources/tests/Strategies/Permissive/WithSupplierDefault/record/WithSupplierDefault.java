package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

import java.util.UUID;
import java.util.function.Supplier;

@Buildable
public record WithSupplierDefault(
		String make,
		String uniqueName,
		double engineSize,
		boolean isElectric,
		float fuelEfficiency
) {

	@Buildable.Defaults
	public static class Defaults {
		public static Supplier<String> uniqueName = () -> "Name: " + UUID.randomUUID();
	}
}
