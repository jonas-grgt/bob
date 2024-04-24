package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE, setterPrefix = "with")
public class SetterWithCustomPrefix {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public SetterWithCustomPrefix(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public SetterWithCustomPrefix setMake(String make) {
		this.make = make;
		return this;
	}

	public SetterWithCustomPrefix setYear(int year) {
		this.year = year;
		return this;
	}
}
