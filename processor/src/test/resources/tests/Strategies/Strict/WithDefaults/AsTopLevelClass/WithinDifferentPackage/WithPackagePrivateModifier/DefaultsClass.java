package io.jonasg.bob.test.foo.bar;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.test.WithPackagePrivateModifier;

@Buildable.Defaults(WithPackagePrivateModifier.class)
public class DefaultsClass {
	static int year = 1985;
	static double engineSize = 1.3;
}