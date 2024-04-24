package io.jonasg.bob.test;

import java.lang.String;

public final class DefaultSetterWithCustomPrefixBuilder implements SetterWithCustomPrefixBuilder, SetterWithCustomPrefixBuilder.BuildStep, SetterWithCustomPrefixBuilder.FuelEfficiencyStep, SetterWithCustomPrefixBuilder.IsElectricStep {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private int year;

  public DefaultSetterWithCustomPrefixBuilder() {
  }

  public DefaultSetterWithCustomPrefixBuilder withEngineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultSetterWithCustomPrefixBuilder withIsElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultSetterWithCustomPrefixBuilder withFuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public DefaultSetterWithCustomPrefixBuilder withMake(String make) {
    this.make = make;
    return this;
  }

  public DefaultSetterWithCustomPrefixBuilder withYear(int year) {
    this.year = year;
    return this;
  }

  public SetterWithCustomPrefix build() {
    var instance = new SetterWithCustomPrefix(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
