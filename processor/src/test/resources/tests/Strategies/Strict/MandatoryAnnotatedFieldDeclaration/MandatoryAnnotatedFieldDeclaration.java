package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public class MandatoryAnnotatedFieldDeclaration {
	@Mandatory
	private String make;

	private String model;

	public MandatoryAnnotatedFieldDeclaration setMake(String make) {
		this.make = make;
		return this;
	}

	public MandatoryAnnotatedFieldDeclaration setModel(String model) {
		this.model = model;
		return this;
	}
}
