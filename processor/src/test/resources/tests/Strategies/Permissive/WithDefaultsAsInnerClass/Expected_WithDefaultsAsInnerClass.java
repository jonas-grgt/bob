package io.jonasg.bob.test;

import java.lang.String;

public final class WithDefaultsAsInnerClassBuilder {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private int year = WithDefaultsAsInnerClass.Defaults.year;

  public WithDefaultsAsInnerClassBuilder() {
  }

  public WithDefaultsAsInnerClassBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder year(int year) {
    this.year = year;
    return this;
  }

  public WithDefaultsAsInnerClass build() {
    var instance = new WithDefaultsAsInnerClass(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
