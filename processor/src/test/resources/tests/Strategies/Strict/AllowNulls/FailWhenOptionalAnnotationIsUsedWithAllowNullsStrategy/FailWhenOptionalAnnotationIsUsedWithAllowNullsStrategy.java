package io.jonasg.bob.test;

import static io.jonasg.bob.Strategy.ALLOW_NULLS;
import static io.jonasg.bob.Strategy.STRICT;
import io.jonasg.bob.Buildable;

@Buildable(strategy = { STRICT, ALLOW_NULLS })
public class FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy {
	private String make;

	@Buildable.Optional
	private int year;

	public FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
