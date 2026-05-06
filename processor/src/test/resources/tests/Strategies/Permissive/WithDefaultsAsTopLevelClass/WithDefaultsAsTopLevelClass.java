package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class WithDefaultsAsTopLevelClass {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithDefaultsAsTopLevelClass(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithDefaultsAsTopLevelClass setMake(String make) {
		this.make = make;
		return this;
	}

	public WithDefaultsAsTopLevelClass setYear(int year) {
		this.year = year;
		return this;
	}

}
