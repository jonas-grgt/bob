package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.BuildableConstructor;

@Buildable
public class UseConstructorAnnotatedWithBuildableConstructor {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public UseConstructorAnnotatedWithBuildableConstructor() {
	}

	public UseConstructorAnnotatedWithBuildableConstructor(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public UseConstructorAnnotatedWithBuildableConstructor(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
		this.make = make;
		this.year = year;
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	@BuildableConstructor
	public UseConstructorAnnotatedWithBuildableConstructor(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
