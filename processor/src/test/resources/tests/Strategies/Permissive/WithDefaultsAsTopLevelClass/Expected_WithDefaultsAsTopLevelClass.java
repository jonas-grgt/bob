package io.jonasg.bob.test;

import java.lang.String;

public final class WithDefaultsAsTopLevelClassBuilder {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private int year = DefaultsClass.year;

  public WithDefaultsAsTopLevelClassBuilder() {
  }

  public WithDefaultsAsTopLevelClassBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public WithDefaultsAsTopLevelClassBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public WithDefaultsAsTopLevelClassBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public WithDefaultsAsTopLevelClassBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithDefaultsAsTopLevelClassBuilder year(int year) {
    this.year = year;
    return this;
  }

  public WithDefaultsAsTopLevelClass build() {
    var instance = new WithDefaultsAsTopLevelClass(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
