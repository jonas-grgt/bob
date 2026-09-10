package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class FieldHiding extends Vehicle {
	private String make;

	public FieldHiding(String make) {
		super(make);
		this.make = make;
	}
}
