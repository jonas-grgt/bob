package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class RecursiveBound<T extends Comparable<T>> {
	private T value;

	public RecursiveBound(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
