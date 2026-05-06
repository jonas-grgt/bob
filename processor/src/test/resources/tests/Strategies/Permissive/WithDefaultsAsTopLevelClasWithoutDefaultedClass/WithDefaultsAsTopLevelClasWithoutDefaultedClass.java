package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class WithDefaultsAsTopLevelClasWithoutDefaultedClass {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithDefaultsAsTopLevelClasWithoutDefaultedClass(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithDefaultsAsTopLevelClasWithoutDefaultedClass setMake(String make) {
		this.make = make;
		return this;
	}

	public WithDefaultsAsTopLevelClasWithoutDefaultedClass setYear(int year) {
		this.year = year;
		return this;
	}

}
