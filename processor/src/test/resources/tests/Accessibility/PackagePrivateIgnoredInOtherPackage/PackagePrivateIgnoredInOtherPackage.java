package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable(packageName = "io.jonasg.bob.test.other")
public class PackagePrivateIgnoredInOtherPackage {
	private String make;

	private int year;

	private double engineSize;

	public PackagePrivateIgnoredInOtherPackage(String make) {
		this(make, 0);
	}

	PackagePrivateIgnoredInOtherPackage(String make, int year) {
		this.make = make;
		this.year = year;
	}

	void setYear(int year) {
		this.year = year;
	}

	public void setEngineSize(double engineSize) {
		this.engineSize = engineSize;
	}
}
