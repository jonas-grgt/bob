package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class UnmatchedShortParam {
	private String name;

	private final short received;

	public UnmatchedShortParam(String name, short flags) {
		this.name = name;
		this.received = flags;
	}

	public String getName() {
		return name;
	}

	public short getReceived() {
		return received;
	}
}
