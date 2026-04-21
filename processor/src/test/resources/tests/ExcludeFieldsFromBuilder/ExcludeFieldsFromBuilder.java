package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(excludeFields = {"year"})
public class ExcludeFieldsFromBuilder {
	private String make;

	private int year;

	private double engineSize;

	public ExcludeFieldsFromBuilder(String make, int year, double engineSize) {
		this.make = make;
		this.year = year;
		this.engineSize = engineSize;
	}
}
