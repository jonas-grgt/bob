package io.jonasg.bob.test;

public class UnmatchedCharParamUsage {
	public static String run() {
		UnmatchedCharParam built = new UnmatchedCharParamBuilder().name("foo").build();
		char received = built.getReceived();
		return built.getName() + "/" + (received == '\u0000');
	}
}
