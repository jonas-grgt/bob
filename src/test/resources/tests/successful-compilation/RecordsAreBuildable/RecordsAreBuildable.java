package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import java.lang.String;

@Buildable
public record RecordsAreBuildable(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
}
