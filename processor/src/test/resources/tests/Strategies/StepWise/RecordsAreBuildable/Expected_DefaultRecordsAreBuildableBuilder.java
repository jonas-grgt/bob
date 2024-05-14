package io.jonasg.bob.test;

import java.lang.String;

public final class DefaultRecordsAreBuildableBuilder implements RecordsAreBuildableBuilder.BuildStep, RecordsAreBuildableBuilder.EngineSizeStep, RecordsAreBuildableBuilder.IsElectricStep, RecordsAreBuildableBuilder.YearStep, RecordsAreBuildableBuilder.FuelEfficiencyStep, RecordsAreBuildableBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public DefaultRecordsAreBuildableBuilder() {
  }

  public DefaultRecordsAreBuildableBuilder make(String make) {
    this.make = make;
    return this;
  }

  public DefaultRecordsAreBuildableBuilder year(int year) {
    this.year = year;
    return this;
  }

  public DefaultRecordsAreBuildableBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultRecordsAreBuildableBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultRecordsAreBuildableBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public RecordsAreBuildable build() {
    return new RecordsAreBuildable(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
