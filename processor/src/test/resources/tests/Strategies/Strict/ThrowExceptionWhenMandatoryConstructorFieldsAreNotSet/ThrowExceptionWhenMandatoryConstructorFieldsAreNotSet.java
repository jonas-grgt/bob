package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import java.lang.String;

@Buildable(strategy = Strategy.STRICT)
public class ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet() {
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet setMake(String make) {
		this.make = make;
		return this;
	}

	public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet setYear(int year) {
		this.year = year;
		return this;
	}
}
