package io.jonasg.bob.test;

public class OverloadedSettersUsage {
	public static String run() {
		OverloadedSetters built = new OverloadedSettersBuilder().make("volvo").year(2020).build();
		return built.getMake() + "/" + built.getYear();
	}
}
