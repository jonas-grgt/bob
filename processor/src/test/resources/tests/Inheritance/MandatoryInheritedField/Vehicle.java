package io.jonasg.bob.test;

import io.jonasg.bob.Buildable.Mandatory;

public class Vehicle {
	@Mandatory
	private String make;

	public Vehicle(String make) {
		this.make = make;
	}
}
