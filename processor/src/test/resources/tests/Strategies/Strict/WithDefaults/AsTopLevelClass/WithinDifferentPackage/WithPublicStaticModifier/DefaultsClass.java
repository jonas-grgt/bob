package io.jonasg.bob.test.foo.bar;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.test.WithPublicStaticModifier;

@Buildable.Defaults(WithPublicStaticModifier.class)
public class DefaultsClass {
	public static int year = 1985;
	public static double engineSize = 1.3;
}