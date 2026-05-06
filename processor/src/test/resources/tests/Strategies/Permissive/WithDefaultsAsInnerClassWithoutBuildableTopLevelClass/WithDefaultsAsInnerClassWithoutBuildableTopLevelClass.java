package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

public class WithDefaultsAsInnerClassWithoutBuildableTopLevelClass {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithDefaultsAsInnerClassWithoutBuildableTopLevelClass(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithDefaultsAsInnerClassWithoutBuildableTopLevelClass setMake(String make) {
		this.make = make;
		return this;
	}

	public WithDefaultsAsInnerClassWithoutBuildableTopLevelClass setYear(int year) {
		this.year = year;
		return this;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static int year = 1985;
	}
}
