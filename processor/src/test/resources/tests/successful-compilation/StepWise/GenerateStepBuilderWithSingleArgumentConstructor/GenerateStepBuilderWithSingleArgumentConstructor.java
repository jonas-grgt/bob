package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.ConstructorPolicy;
import java.lang.String;

@Buildable(constructorPolicy = ConstructorPolicy.ENFORCED_STEPWISE)
public class GenerateStepBuilderWithSingleArgumentConstructor {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public GenerateStepBuilderWithSingleArgumentConstructor() {
	}

	public GenerateStepBuilderWithSingleArgumentConstructor(int year) {
		this.make = make;
		this.year = year;
	}

	public GenerateStepBuilderWithSingleArgumentConstructor setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public GenerateStepBuilderWithSingleArgumentConstructor setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public GenerateStepBuilderWithSingleArgumentConstructor setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public GenerateStepBuilderWithSingleArgumentConstructor setMake(String make) {
		this.make = make;
		return this;
	}

	public GenerateStepBuilderWithSingleArgumentConstructor setYear(int year) {
		this.year = year;
		return this;
	}
}
