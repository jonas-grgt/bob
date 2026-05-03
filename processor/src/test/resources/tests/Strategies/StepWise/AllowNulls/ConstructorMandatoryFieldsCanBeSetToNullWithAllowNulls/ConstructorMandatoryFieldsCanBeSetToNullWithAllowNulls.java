package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import java.lang.String;

@Buildable(strategy = {Strategy.STEP_WISE, Strategy.ALLOW_NULLS})
public class ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls {
	private String make;

	private Integer year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls() {
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls(String make, Integer year) {
		this.make = make;
		this.year = year;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setIsElectric(boolean isElectric) {
		this.isElectric = isElectric;
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

	public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls setYear(Integer year) {
		this.year = year;
		return this;
	}
}
