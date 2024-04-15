package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class MultipleBuildableConstructorAnnotationsPresent {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public MultipleBuildableConstructorAnnotationsPresent() {
	}

	public MultipleBuildableConstructorAnnotationsPresent(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	@Buildable.Constructor
	public MultipleBuildableConstructorAnnotationsPresent(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
		this.make = make;
		this.year = year;
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	@Buildable.Constructor
	public MultipleBuildableConstructorAnnotationsPresent(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
