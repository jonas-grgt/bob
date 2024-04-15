package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode {
	private String make;

	@Buildable.Mandatory
	private int year;

	private double engineSize;

	private boolean isElectric;

	@Buildable.Mandatory
	private float fuelEfficiency;

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode() {
	}

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setMake(String make) {
		this.make = make;
		return this;
	}

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setYear(int year) {
		this.year = year;
		return this;
	}

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setEngineSize(double engineSize) {
		this.engineSize = engineSize;
		return this;
	}

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setElectric(boolean electric) {
		isElectric = electric;
		return this;
	}

	public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode setFuelEfficiency(float fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
		return this;
	}
}
