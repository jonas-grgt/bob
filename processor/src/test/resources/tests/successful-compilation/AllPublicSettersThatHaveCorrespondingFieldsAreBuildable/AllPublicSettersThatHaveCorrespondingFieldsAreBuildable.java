package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class AllPublicSettersThatHaveCorrespondingFieldsAreBuildable {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable() {
	}

	public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}
}
