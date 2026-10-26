package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable.Defaults(DuplicatedCar.class)
public class FirstDuplicatedCarDefaults {
	public static int year = 1985;
}
