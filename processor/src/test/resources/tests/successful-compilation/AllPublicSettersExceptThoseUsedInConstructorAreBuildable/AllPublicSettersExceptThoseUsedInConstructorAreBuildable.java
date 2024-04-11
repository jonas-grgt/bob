package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class AllPublicSettersExceptThoseUsedInConstructorAreBuildable {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable() {
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable setMake(String make) {
		this.make = make;
		return this;
	}

	public AllPublicSettersExceptThoseUsedInConstructorAreBuildable setYear(int year) {
		this.year = year;
		return this;
	}
}
