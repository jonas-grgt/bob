package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class RedundantMandatoryAnnotationOnConstructorParam {
	private String make;

	private int year;

	public RedundantMandatoryAnnotationOnConstructorParam(@Mandatory String make, int year) {
		this.make = make;
		this.year = year;
	}

	public RedundantMandatoryAnnotationOnConstructorParam setMake(String make) {
		this.make = make;
		return this;
	}

	public RedundantMandatoryAnnotationOnConstructorParam setYear(int year) {
		this.year = year;
		return this;
	}
}
