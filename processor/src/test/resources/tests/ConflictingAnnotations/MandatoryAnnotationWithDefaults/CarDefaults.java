package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(MandatoryAnnotationWithDefaults.class)
public class CarDefaults {
	public static String make = "BMW";
}
