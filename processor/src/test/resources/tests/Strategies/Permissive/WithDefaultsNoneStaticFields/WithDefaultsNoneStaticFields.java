package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class WithDefaultsNoneStaticFields {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithDefaultsNoneStaticFields(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithDefaultsNoneStaticFields setMake(String make) {
		this.make = make;
		return this;
	}

	public WithDefaultsNoneStaticFields setYear(int year) {
		this.year = year;
		return this;
	}

	@Buildable.Defaults
	public static class Defaults {
		public int year = 1985;
	}
}
