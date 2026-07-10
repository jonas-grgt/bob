package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

import java.util.UUID;
import java.util.function.Supplier;

@Buildable
public class WithSupplierDefault {
	private String make;

	private String uniqueName;

	private double engineSize;

	private boolean isElectric;

	private float fuelEfficiency;

	public WithSupplierDefault(double engineSize, boolean isElectric, float fuelEfficiency) {
		this.engineSize = engineSize;
		this.isElectric = isElectric;
		this.fuelEfficiency = fuelEfficiency;
	}

	public WithSupplierDefault setMake(String make) {
		this.make = make;
		return this;
	}

	public WithSupplierDefault setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
		return this;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static Supplier<String> uniqueName = () -> "Name: " + UUID.randomUUID();
	}
}
