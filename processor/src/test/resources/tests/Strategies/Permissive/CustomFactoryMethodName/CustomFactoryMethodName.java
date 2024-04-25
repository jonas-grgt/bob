package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(factoryName = "customName")
public class CustomFactoryMethodName {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public CustomFactoryMethodName(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public CustomFactoryMethodName setMake(String make) {
		this.make = make;
		return this;
	}

	public CustomFactoryMethodName setYear(int year) {
		this.year = year;
		return this;
	}
}
