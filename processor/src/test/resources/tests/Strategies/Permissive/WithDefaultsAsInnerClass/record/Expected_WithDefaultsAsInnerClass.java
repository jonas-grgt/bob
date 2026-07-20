package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class WithDefaultsAsInnerClassBuilder {
  private String make;

  private int year = WithDefaultsAsInnerClass.Defaults.year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public WithDefaultsAsInnerClassBuilder() {
  }

  public WithDefaultsAsInnerClassBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder year(int year) {
    this.year = year;
    return this;
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

  public WithDefaultsAsInnerClass build() {
    return new WithDefaultsAsInnerClass(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
