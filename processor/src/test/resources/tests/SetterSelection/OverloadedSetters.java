package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class OverloadedSetters {
	private String make;

	private int year;

	public OverloadedSetters() {
	}

	public void setMake(Object make) {
		this.make = "object:" + make;
	}

	public void setMake(int make) {
		this.make = "int:" + make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setYear(long year) {
		this.year = -1;
	}

	public void setYear(Object year) {
		this.year = -2;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public int getYear() {
		return year;
	}
}
