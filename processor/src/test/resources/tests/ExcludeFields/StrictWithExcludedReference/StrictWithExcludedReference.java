package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT, excludeFields = {"model", "color"})
public class StrictWithExcludedReference {
	private String make;

	private String model;

	private String color;

	public StrictWithExcludedReference(String make, String model) {
		this.make = make;
		this.model = model;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getColor() {
		return color;
	}
}
