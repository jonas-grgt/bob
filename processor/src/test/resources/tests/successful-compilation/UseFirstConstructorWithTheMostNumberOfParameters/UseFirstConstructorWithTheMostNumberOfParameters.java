package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class UseFirstConstructorWithTheMostNumberOfParameters {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public UseFirstConstructorWithTheMostNumberOfParameters() {
	}

	public UseFirstConstructorWithTheMostNumberOfParameters(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public UseFirstConstructorWithTheMostNumberOfParameters(String make, boolean isElectric, float fuelEfficiency) {
		this.make = make;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public UseFirstConstructorWithTheMostNumberOfParameters(int year, boolean isElectric, float fuelEfficiency) {
		this.year = year;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public UseFirstConstructorWithTheMostNumberOfParameters(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
