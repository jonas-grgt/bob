package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(WithDefaultsAsTopLevelClass.class)
public class DefaultsClass {
	public static int year = 1985;
}