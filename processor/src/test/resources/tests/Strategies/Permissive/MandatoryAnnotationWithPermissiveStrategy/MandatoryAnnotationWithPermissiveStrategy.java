package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;

import java.lang.String;

@Buildable
public class MandatoryAnnotationWithPermissiveStrategy {
	private String make;

	private int year;

	@Mandatory
	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public MandatoryAnnotationWithPermissiveStrategy() {
	}

	public MandatoryAnnotationWithPermissiveStrategy(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public MandatoryAnnotationWithPermissiveStrategy setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public MandatoryAnnotationWithPermissiveStrategy setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public MandatoryAnnotationWithPermissiveStrategy setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public MandatoryAnnotationWithPermissiveStrategy setMake(String make) {
		this.make = make;
		return this;
	}

	public MandatoryAnnotationWithPermissiveStrategy setYear(int year) {
		this.year = year;
		return this;
	}
}
