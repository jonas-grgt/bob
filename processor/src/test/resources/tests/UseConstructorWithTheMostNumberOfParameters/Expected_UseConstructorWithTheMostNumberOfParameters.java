package io.jonasg.bob.test;

import java.lang.String;

public final class UseConstructorWithTheMostNumberOfParametersBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public UseConstructorWithTheMostNumberOfParametersBuilder() {
  }

  public UseConstructorWithTheMostNumberOfParametersBuilder make(String make) {
    this.make = make;
    return this;
  }

  public UseConstructorWithTheMostNumberOfParametersBuilder year(int year) {
    this.year = year;
    return this;
  }

  public UseConstructorWithTheMostNumberOfParametersBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public UseConstructorWithTheMostNumberOfParametersBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public UseConstructorWithTheMostNumberOfParametersBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public UseConstructorWithTheMostNumberOfParameters build() {
    return new UseConstructorWithTheMostNumberOfParameters(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
