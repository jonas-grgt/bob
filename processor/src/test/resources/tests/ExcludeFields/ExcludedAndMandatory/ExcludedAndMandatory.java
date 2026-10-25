package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT, excludeFields = "year", mandatoryFields = "year")
public class ExcludedAndMandatory {
	private String make;

	private int year;

	public ExcludedAndMandatory(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
