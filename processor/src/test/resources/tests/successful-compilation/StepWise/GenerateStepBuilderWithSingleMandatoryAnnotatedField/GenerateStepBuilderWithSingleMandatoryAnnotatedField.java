package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.ConstructorPolicy;

@Buildable(constructorPolicy = ConstructorPolicy.ENFORCED_STEPWISE)
public class GenerateStepBuilderWithSingleMandatoryAnnotatedField {
	private int year;

	@Mandatory
	private String make;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField() {
	}

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField setMake(String make) {
		this.make = make;
		return this;
	}

	public GenerateStepBuilderWithSingleMandatoryAnnotatedField setYear(int year) {
		this.year = year;
		return this;
	}
}
