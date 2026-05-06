package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class WithDefaultsAsInnerClass {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithDefaultsAsInnerClass(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithDefaultsAsInnerClass setMake(String make) {
		this.make = make;
		return this;
	}

	public WithDefaultsAsInnerClass setYear(int year) {
		this.year = year;
		return this;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static int year = 1985;
	}
}
