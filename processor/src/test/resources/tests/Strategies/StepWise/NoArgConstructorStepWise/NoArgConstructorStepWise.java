package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE)
public class NoArgConstructorStepWise {
	private int year;

	public NoArgConstructorStepWise() {
	}

	public NoArgConstructorStepWise setYear(int year) {
		this.year = year;
		return this;
	}
}
