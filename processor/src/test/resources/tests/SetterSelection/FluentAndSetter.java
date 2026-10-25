package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class FluentAndSetter {
	private String make;

	private int year;

	public FluentAndSetter() {
	}

	public FluentAndSetter make(String make) {
		this.make = make;
		return this;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public FluentAndSetter year(int year) {
		this.year = year;
		return this;
	}

	public String getMake() {
		return make;
	}

	public int getYear() {
		return year;
	}
}
