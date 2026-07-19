package io.jonasg.bob.test;

import java.lang.String;

public final class RecordsAreBuildableBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public RecordsAreBuildableBuilder() {
  }

  public RecordsAreBuildableBuilder make(String make) {
    this.make = make;
    return this;
  }

  public RecordsAreBuildableBuilder year(int year) {
    this.year = year;
    return this;
  }

  public RecordsAreBuildableBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public RecordsAreBuildableBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public RecordsAreBuildableBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public RecordsAreBuildable build() {
    return new RecordsAreBuildable(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
