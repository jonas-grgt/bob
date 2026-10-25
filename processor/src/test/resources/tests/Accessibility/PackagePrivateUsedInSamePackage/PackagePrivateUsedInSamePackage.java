package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class PackagePrivateUsedInSamePackage {
	private String make;

	private int year;

	private double engineSize;

	public PackagePrivateUsedInSamePackage(String make) {
		this(make, 0);
	}

	PackagePrivateUsedInSamePackage(String make, int year) {
		this.make = make;
		this.year = year;
	}

	void setEngineSize(double engineSize) {
		this.engineSize = engineSize;
	}
}
