package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class UnmatchedCharParam {
	private String name;

	private final char received;

	public UnmatchedCharParam(String name, char flags) {
		this.name = name;
		this.received = flags;
	}

	public String getName() {
		return name;
	}

	public char getReceived() {
		return received;
	}
}
