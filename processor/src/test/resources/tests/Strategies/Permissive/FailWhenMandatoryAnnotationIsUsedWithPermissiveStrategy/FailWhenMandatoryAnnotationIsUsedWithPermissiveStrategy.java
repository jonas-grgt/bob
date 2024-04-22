package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;

import java.lang.String;

@Buildable
public class FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy {
	private String make;

	private int year;

	@Mandatory
	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy() {
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy setMake(String make) {
		this.make = make;
		return this;
	}

	public FailWhenMandatoryAnnotationIsUsedWithPermissiveStrategy setYear(int year) {
		this.year = year;
		return this;
	}
}
