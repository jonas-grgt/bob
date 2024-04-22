package io.jonasg.bob.test;

import java.lang.String;

public final class AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder() {
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder year(int year) {
    this.year = year;
    return this;
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforcedBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public AllConstructorParamsAreBuildableAndByDefaultNotEnforced build() {
    return new AllConstructorParamsAreBuildableAndByDefaultNotEnforced(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
