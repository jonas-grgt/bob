package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class GenericCar<T> {
	private T cargo;

	private int year;

	private String make;

	public GenericCar(T cargo, int year, String make) {
		this.cargo = cargo;
		this.year = year;
		this.make = make;
	}

	public T getCargo() {
		return cargo;
	}

	public int getYear() {
		return year;
	}

	public String getMake() {
		return make;
	}
}
