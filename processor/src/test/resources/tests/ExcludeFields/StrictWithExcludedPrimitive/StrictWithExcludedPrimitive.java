package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT, excludeFields = "year")
public class StrictWithExcludedPrimitive {
	private String make;

	private int year;

	public StrictWithExcludedPrimitive(String make, int year) {
		this.make = make;
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public int getYear() {
		return year;
	}
}
