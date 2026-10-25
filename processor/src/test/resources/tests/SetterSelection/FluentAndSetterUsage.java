package io.jonasg.bob.test;

public class FluentAndSetterUsage {
	public static String run() {
		FluentAndSetter built = new FluentAndSetterBuilder().make("volvo").year(2020).build();
		return built.getMake() + "/" + built.getYear();
	}
}
