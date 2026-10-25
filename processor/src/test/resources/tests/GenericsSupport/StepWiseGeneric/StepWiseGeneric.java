package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STEP_WISE)
public class StepWiseGeneric<K, V extends Comparable<V>> {
	private K key;

	private V value;

	private String note;

	public StepWiseGeneric(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public String getNote() {
		return note;
	}
}
