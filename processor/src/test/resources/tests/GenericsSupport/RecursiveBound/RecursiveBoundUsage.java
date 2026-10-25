package io.jonasg.bob.test;

public class RecursiveBoundUsage {
	public static String run() {
		RecursiveBound<Integer> built = new RecursiveBoundBuilder<Integer>().value(5).build();
		Integer value = built.getValue();
		return "compare=" + value.compareTo(3);
	}
}
