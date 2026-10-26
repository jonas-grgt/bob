package io.jonasg.bob.test;

public class GenericCarUsage {
	public static String run() {
		GenericCar<String> built = new GenericCarBuilder<String>().cargo("box").build();
		String cargo = built.getCargo();
		return cargo + "/" + built.getYear() + "/" + built.getMake();
	}
}
