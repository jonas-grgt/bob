package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(PlainCar.class)
public class PlainCarDefaults {
	public static int year = 1985;

	public static String make = "volvo";
}
