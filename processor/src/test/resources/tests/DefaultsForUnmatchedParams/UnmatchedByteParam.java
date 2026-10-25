package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class UnmatchedByteParam {
	private String name;

	private final byte received;

	public UnmatchedByteParam(String name, byte flags) {
		this.name = name;
		this.received = flags;
	}

	public String getName() {
		return name;
	}

	public byte getReceived() {
		return received;
	}
}
