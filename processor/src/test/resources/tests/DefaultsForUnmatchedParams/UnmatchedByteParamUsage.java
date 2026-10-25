package io.jonasg.bob.test;

public class UnmatchedByteParamUsage {
	public static String run() {
		UnmatchedByteParam built = new UnmatchedByteParamBuilder().name("foo").build();
		byte received = built.getReceived();
		return built.getName() + "/" + (received == (byte) 0);
	}
}
