package io.jonasg.bob.test;

import java.lang.String;

public final class AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder() {
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder year(int year) {
    this.year = year;
    return this;
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildableBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public AllPublicSettersThatHaveCorrespondingFieldsAreBuildable build() {
    var instance = new AllPublicSettersThatHaveCorrespondingFieldsAreBuildable(make, year);
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
