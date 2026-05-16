package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public record WithDefaultsAsInnerClass(
		String make,
		int year,
		double engineSize,
		boolean isElectric,
		float fuelEfficiency
) {

	@Buildable.Defaults
	public static class Defaults {
		public static int year = 1985;
	}
}
