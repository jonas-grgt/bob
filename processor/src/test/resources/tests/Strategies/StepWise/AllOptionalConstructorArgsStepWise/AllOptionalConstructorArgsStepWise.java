package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE)
public class AllOptionalConstructorArgsStepWise {
	private int year;

	public AllOptionalConstructorArgsStepWise(@Buildable.Optional int year) {
		this.year = year;
	}
}
