package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import java.lang.String;

@Buildable(strategy = Strategy.STEP_WISE, Strategy.ALLOW_NULLS)
public class ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls {
	private String make;

	private integer year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls() {
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise(String make, integer year) {
		this.make = make;
		this.year = year;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls setMake(String make) {
		this.make = make;
		return this;
	}

	public ConstructorMandatoryFieldsCanBeSetToNullWIthAllowNulls setYear(integer year) {
		this.year = year;
		return this;
	}
}
