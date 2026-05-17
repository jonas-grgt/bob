package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(MultipleMandatoryFieldsWithDefaults.class)
public class CarDefaults {
	public static String make = "BMW";
	public static String model = "M3";
}
