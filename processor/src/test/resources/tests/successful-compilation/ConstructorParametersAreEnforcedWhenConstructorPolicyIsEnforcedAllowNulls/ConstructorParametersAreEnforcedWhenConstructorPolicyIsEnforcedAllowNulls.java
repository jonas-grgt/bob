package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.ConstructorPolicy;
import java.lang.String;
import java.lang.Integer;

@Buildable(constructorPolicy = ConstructorPolicy.ENFORCED_ALLOW_NULLS)
public class ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls() {
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls setMake(String make) {
		this.make = make;
		return this;
	}

	public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls setYear(int year) {
		this.year = year;
		return this;
	}
}
