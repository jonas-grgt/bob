package io.jonasg.bob.test;

import static io.jonasg.bob.Strategy.ALLOW_NULLS;
import static io.jonasg.bob.Strategy.STRICT;
import io.jonasg.bob.Buildable;

@Buildable(strategy = { STRICT, ALLOW_NULLS })
public class ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls() {
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setMake(String make) {
		this.make = make;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setYear(int year) {
		this.year = year;
		return this;
	}
}
