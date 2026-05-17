package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(mandatoryFields = {"unknownField"})
public class UnknownMandatoryField {
	private String make;
	private int year;

	public UnknownMandatoryField(String make) {
		this.make = make;
	}

	public UnknownMandatoryField setYear(int year) {
		this.year = year;
		return this;
	}
}
