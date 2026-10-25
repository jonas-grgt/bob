package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class PrivateSetterIsIgnored {
	private String make;

	private int year;

	public PrivateSetterIsIgnored() {
	}

	private void setMake(String make) {
		this.make = make;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
