package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(WithPackagePrivateModifier.class)
public class DefaultsClass {
	static int year = 1985;
	static double engineSize = 1.3;
}