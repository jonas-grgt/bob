package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class WithPublicStaticModifier {
	private String make;

	private int year;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithPublicStaticModifier(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithPublicStaticModifier setMake(String make) {
		this.make = make;
		return this;
	}

	public WithPublicStaticModifier setYear(int year) {
		this.year = year;
		return this;
	}

}
