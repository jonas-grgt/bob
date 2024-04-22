package io.jonasg.bob.test;

import static io.jonasg.bob.Strategy.ALLOW_NULLS;
import static io.jonasg.bob.Strategy.STRICT;
import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;

@Buildable(strategy = { STRICT, ALLOW_NULLS }, mandatoryFields = {"fuelEfficiency", "isElectric"})
public class FieldsDeclaredInBuildableAnnotationCanBeSetToNull {
	private String make;

	private int year;

	private double engineSize;

	@Mandatory
	private boolean isElectric;

	@Mandatory
	private float fuelEfficiency;

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull() {
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull setIsElectric(boolean isElectric) {
		isElectric = isElectric;
		return this;
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull setMake(String make) {
		this.make = make;
		return this;
	}

	public FieldsDeclaredInBuildableAnnotationCanBeSetToNull setYear(int year) {
		this.year = year;
		return this;
	}
}
