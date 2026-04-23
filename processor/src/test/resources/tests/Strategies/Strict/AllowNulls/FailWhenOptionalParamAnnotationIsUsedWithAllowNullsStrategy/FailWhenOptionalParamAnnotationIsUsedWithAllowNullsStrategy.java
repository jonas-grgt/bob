package io.jonasg.bob.test;

import static io.jonasg.bob.Strategy.ALLOW_NULLS;
import static io.jonasg.bob.Strategy.STRICT;
import io.jonasg.bob.Buildable;

@Buildable(strategy = { STRICT, ALLOW_NULLS })
public class FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy {
	private String make;

	private int year;

	public FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy(String make, @Buildable.Optional int year) {
		this.make = make;
		this.year = year;
	}
}
