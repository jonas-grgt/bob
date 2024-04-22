package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import java.lang.String;

@Buildable(strategy = Strategy.STEP_WISE)
public class GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise() {
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise setMake(String make) {
		this.make = make;
		return this;
	}

	public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise setYear(int year) {
		this.year = year;
		return this;
	}
}
