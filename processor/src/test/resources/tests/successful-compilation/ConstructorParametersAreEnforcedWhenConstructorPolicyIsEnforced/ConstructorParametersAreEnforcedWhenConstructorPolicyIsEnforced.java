package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.ConstructorPolicy;
import java.lang.String;
import java.lang.Integer;

@Buildable(constructorPolicy = ConstructorPolicy.ENFORCED)
public class ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced() {
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced setMake(String make) {
		this.make = make;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced setYear(int year) {
		this.year = year;
		return this;
	}
}
