package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(InheritedFieldWithDefaults.class)
public class InheritedFieldDefaults {
	public static String make = "Toyota";
}
