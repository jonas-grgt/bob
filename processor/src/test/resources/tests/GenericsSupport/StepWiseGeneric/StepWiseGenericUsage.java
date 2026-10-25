package io.jonasg.bob.test;

public class StepWiseGenericUsage {
	public static String run() {
		StepWiseGenericBuilder<String, Integer> start = StepWiseGenericBuilder.newBuilder();
		StepWiseGeneric<String, Integer> built = start.key("a").value(7).note("n").build();
		String key = built.getKey();
		Integer value = built.getValue();
		return key + "/" + value.compareTo(7) + "/" + built.getNote();
	}
}
