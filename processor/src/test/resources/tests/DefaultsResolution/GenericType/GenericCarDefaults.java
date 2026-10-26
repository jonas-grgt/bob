package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(GenericCar.class)
public class GenericCarDefaults {
	public static int year = 1985;

	public static String make = "volvo";
}
