package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(MandatoryFieldsWithDefaults.class)
public class CarDefaults {
	public static String model = "Model S";
}
