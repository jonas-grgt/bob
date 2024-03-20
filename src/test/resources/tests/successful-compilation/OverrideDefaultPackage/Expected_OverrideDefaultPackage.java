package io.jonasg.bob.test.other;

import io.jonasg.bob.test.OverrideDefaultPackage;
import java.lang.String;

public final class OverrideDefaultPackageBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public OverrideDefaultPackageBuilder() {
  }

  public OverrideDefaultPackageBuilder make(String make) {
    this.make = make;
    return this;
  }

  public OverrideDefaultPackageBuilder year(int year) {
    this.year = year;
    return this;
  }

  public OverrideDefaultPackageBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public OverrideDefaultPackageBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public OverrideDefaultPackageBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public OverrideDefaultPackage build() {
    return new OverrideDefaultPackage(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
