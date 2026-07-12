package io.jonasg.bob.test;

import io.jonasg.bob.TestDefaultsResolver;
import java.lang.String;

public final class WithDefaultsAsInnerClassBuilder {
  private String make;

  private int year = WithDefaultsAsInnerClass.Defaults.year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public WithDefaultsAsInnerClassBuilder() {
    TestDefaultsResolver.applyDefaults(this, WithDefaultsAsInnerClass.class);
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
