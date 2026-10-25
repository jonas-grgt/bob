package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class PrivateConstructorIsIgnored {
	private String make;

	private int year;

	public PrivateConstructorIsIgnored(String make) {
		this(make, 0);
	}

	private PrivateConstructorIsIgnored(String make, int year) {
		this.make = make;
		this.year = year;
	}
}
