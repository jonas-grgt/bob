package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class MandatoryAnnotationWithDefaults {
	@Mandatory
	private String make;

	public MandatoryAnnotationWithDefaults() {
	}

	public MandatoryAnnotationWithDefaults setMake(String make) {
		this.make = make;
		return this;
	}
}
