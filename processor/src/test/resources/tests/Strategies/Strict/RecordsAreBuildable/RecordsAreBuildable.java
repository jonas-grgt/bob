package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public record RecordsAreBuildable(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
}
