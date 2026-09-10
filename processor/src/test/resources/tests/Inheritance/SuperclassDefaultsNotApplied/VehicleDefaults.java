package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(Vehicle.class)
public class VehicleDefaults {
	public static String make = "Toyota";
}
