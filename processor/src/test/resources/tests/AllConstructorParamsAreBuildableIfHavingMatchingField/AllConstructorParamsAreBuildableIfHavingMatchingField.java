package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class AllConstructorParamsAreBuildableIfHavingMatchingField {
	private String make;

	private double engineSize;

	private boolean isElectric;

	public AllConstructorParamsAreBuildableIfHavingMatchingField(String make, Integer year, double engineSize, boolean isElectric, Float fuelEfficiency) {
		this.make = make;
		this.engineSize = engineSize;
		this.isElectric = isElectric;
	}
}
