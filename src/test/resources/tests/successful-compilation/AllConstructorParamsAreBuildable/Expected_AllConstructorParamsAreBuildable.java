package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.AllConstructorParamsAreBuildable;
import java.lang.String;

public final class AllConstructorParamsAreBuildableBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public AllConstructorParamsAreBuildableBuilder() {
  }

  public AllConstructorParamsAreBuildableBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AllConstructorParamsAreBuildableBuilder year(int year) {
    this.year = year;
    return this;
  }

  public AllConstructorParamsAreBuildableBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AllConstructorParamsAreBuildableBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public AllConstructorParamsAreBuildableBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public AllConstructorParamsAreBuildable build() {
    return new AllConstructorParamsAreBuildable(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
