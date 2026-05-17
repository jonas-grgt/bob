package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(mandatoryFields = {"model"})
public class MandatoryFieldsWithDefaults {
	private String make;

	private String model;

	public MandatoryFieldsWithDefaults() {
	}

	public MandatoryFieldsWithDefaults setMake(String make) {
		this.make = make;
		return this;
	}

	public MandatoryFieldsWithDefaults setModel(String model) {
		this.model = model;
		return this;
	}
}
