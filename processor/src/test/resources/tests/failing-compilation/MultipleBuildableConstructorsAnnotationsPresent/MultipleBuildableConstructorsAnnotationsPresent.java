package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class MultipleBuildableConstructorsAnnotationsPresent {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public MultipleBuildableConstructorsAnnotationsPresent() {
	}

	public MultipleBuildableConstructorsAnnotationsPresent(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	@Buildable.Constructor
	public MultipleBuildableConstructorsAnnotationsPresent(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
		this.make = make;
		this.year = year;
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	@Buildable.Constructor
	public MultipleBuildableConstructorsAnnotationsPresent(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
