package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(prefix = "with")
public class SetterWithCustomPrefix {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public SetterWithCustomPrefix(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
		this.make = make;
		this.year = year;
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}
}
