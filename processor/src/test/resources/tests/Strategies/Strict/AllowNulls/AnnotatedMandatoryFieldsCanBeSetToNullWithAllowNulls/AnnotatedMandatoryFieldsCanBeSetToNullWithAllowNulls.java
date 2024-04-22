package io.jonasg.bob.test;

import static io.jonasg.bob.Strategy.ALLOW_NULLS;
import static io.jonasg.bob.Strategy.STRICT;
import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;

@Buildable(strategy = { STRICT, ALLOW_NULLS })
public class AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls {
	private String make;

	private int year;

	private double engineSize;

	@Mandatory
	private boolean isElectric;

	@Mandatory
	private float fuelEfficiency;

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls() {
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls setMake(String make) {
		this.make = make;
		return this;
	}

	public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls setYear(int year) {
		this.year = year;
		return this;
	}
}
