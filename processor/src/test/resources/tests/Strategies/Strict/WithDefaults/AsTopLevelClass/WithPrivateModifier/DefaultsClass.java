package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(WithPrivateModifier.class)
public class DefaultsClass {
	private static int year = 1985;
	private static double engineSize = 1.3;
}