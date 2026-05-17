package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class MultipleMandatoryFieldsWithDefaults {
	@Mandatory
	private String make;

	@Mandatory
	private String model;

	public MultipleMandatoryFieldsWithDefaults() {
	}

	public MultipleMandatoryFieldsWithDefaults setMake(String make) {
		this.make = make;
		return this;
	}

	public MultipleMandatoryFieldsWithDefaults setModel(String model) {
		this.model = model;
		return this;
	}
}
