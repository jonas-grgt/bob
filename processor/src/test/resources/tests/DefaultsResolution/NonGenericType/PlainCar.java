package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class PlainCar {
	private String cargo;

	private int year;

	private String make;

	public PlainCar(String cargo, int year, String make) {
		this.cargo = cargo;
		this.year = year;
		this.make = make;
	}

	public String getCargo() {
		return cargo;
	}

	public int getYear() {
		return year;
	}

	public String getMake() {
		return make;
	}
}
