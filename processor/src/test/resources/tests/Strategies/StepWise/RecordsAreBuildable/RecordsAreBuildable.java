package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

import java.lang.String;

@Buildable(strategy = Strategy.STEP_WISE)
public record RecordsAreBuildable(String make, int year, double engineSize, boolean isElectric, float fuelEfficiency) {
}