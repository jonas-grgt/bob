package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;
import java.lang.String;

@Buildable(strategy = Strategy.STRICT)
public class ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet {
	private String make;

	private int year;

	@Mandatory
	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet() {
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet setMake(String make) {
		this.make = make;
		return this;
	}

	public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet setYear(int year) {
		this.year = year;
		return this;
	}
}
