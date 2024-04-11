package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.AllPublicSettersExceptThoseUsedInConstructorAreBuildable;
import java.lang.String;

public final class AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder() {
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder year(int year) {
    this.year = year;
    return this;
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildableBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public AllPublicSettersExceptThoseUsedInConstructorAreBuildable build() {
    var instance = new AllPublicSettersExceptThoseUsedInConstructorAreBuildable(make, year);
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
