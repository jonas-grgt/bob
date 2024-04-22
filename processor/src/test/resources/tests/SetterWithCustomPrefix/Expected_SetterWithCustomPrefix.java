package io.jonasg.bob.test;

import java.lang.String;

public final class SetterWithCustomPrefixBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public SetterWithCustomPrefixBuilder() {
  }

  public SetterWithCustomPrefixBuilder withMake(String make) {
    this.make = make;
    return this;
  }

  public SetterWithCustomPrefixBuilder withYear(int year) {
    this.year = year;
    return this;
  }

  public SetterWithCustomPrefixBuilder withEngineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public SetterWithCustomPrefixBuilder withIsElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public SetterWithCustomPrefixBuilder withFuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public SetterWithCustomPrefix build() {
    return new SetterWithCustomPrefix(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
