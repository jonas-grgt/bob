package io.jonasg.bob.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NonJavaLangBoundUsage {
	public static String run() {
		NonJavaLangBound.Vehicle car = new NonJavaLangBound.Vehicle("volvo");
		ArrayList<NonJavaLangBound.Vehicle> fleet = new ArrayList<>(List.of(car));
		NonJavaLangBound<NonJavaLangBound.Vehicle, LocalDate, ArrayList<NonJavaLangBound.Vehicle>> built =
				new NonJavaLangBoundBuilder<NonJavaLangBound.Vehicle, LocalDate, ArrayList<NonJavaLangBound.Vehicle>>()
						.vehicle(car)
						.registeredAt(LocalDate.of(2020, 1, 2))
						.fleet(fleet)
						.build();
		LocalDate date = built.getRegisteredAt();
		return built.getVehicle().getName() + "/" + date.getYear() + "/" + built.getFleet().size();
	}
}
