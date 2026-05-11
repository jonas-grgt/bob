package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(WithPublicStaticModifier.class)
public class DefaultsClass {
	public static int year = 1985;
	public static double engineSize = 1.3;
}