package io.jonasg.bob.test;

public class Vehicle {
	private String make;
	public static String staticField;

	public Vehicle(String make) {
		this.make = make;
	}

	public static void setStaticField(String staticField) {
		Vehicle.staticField = staticField;
	}
}
