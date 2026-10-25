package io.jonasg.bob.test;

public class UnmatchedShortParamUsage {
	public static String run() {
		UnmatchedShortParam built = new UnmatchedShortParamBuilder().name("foo").build();
		short received = built.getReceived();
		return built.getName() + "/" + (received == (short) 0);
	}
}
