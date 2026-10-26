package io.jonasg.bob.test;

public class PlainCarUsage {
	public static String run() {
		PlainCar built = new PlainCarBuilder().cargo("box").build();
		return built.getCargo() + "/" + built.getYear() + "/" + built.getMake();
	}
}
