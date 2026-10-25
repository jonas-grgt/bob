package io.jonasg.bob.test;

import java.time.temporal.Temporal;
import java.util.List;

import io.jonasg.bob.Buildable;

@Buildable
public class NonJavaLangBound<V extends NonJavaLangBound.Vehicle, D extends Temporal, L extends List<V>> {
	private V vehicle;

	private D registeredAt;

	private L fleet;

	public NonJavaLangBound(V vehicle, D registeredAt, L fleet) {
		this.vehicle = vehicle;
		this.registeredAt = registeredAt;
		this.fleet = fleet;
	}

	public V getVehicle() {
		return vehicle;
	}

	public D getRegisteredAt() {
		return registeredAt;
	}

	public L getFleet() {
		return fleet;
	}

	public static class Vehicle {
		private final String name;

		public Vehicle(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
