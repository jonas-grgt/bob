package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;

import java.lang.String;

@Buildable
public record MandatoryAnnotationWithPermissiveStrategy(
		String make,
		int year,
		@Mandatory double engineSize,
		boolean isElectric,
		float fuelEfficiency
) {}
