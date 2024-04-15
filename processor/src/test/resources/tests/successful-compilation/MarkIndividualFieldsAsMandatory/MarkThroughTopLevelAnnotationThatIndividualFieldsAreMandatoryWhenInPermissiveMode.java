package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(mandatoryFields = {"year", "fuelEfficiency"})
public class MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode() {
	}

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setMake(String make) {
		this.make = make;
		return this;
	}

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setYear(int year) {
		this.year = year;
		return this;
	}

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setElectric(boolean electric) {
		isElectric = electric;
		return this;
	}

	public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}
}
