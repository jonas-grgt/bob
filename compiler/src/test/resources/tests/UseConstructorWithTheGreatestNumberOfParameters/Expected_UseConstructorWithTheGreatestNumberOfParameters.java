package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.UseConstructorWithTheGreatestNumberOfParameters;
import java.lang.String;

public final class UseConstructorWithTheGreatestNumberOfParametersBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public UseConstructorWithTheGreatestNumberOfParametersBuilder() {
  }

  public UseConstructorWithTheGreatestNumberOfParametersBuilder make(String make) {
    this.make = make;
    return this;
  }

  public UseConstructorWithTheGreatestNumberOfParametersBuilder year(int year) {
    this.year = year;
    return this;
  }

  public UseConstructorWithTheGreatestNumberOfParametersBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public UseConstructorWithTheGreatestNumberOfParametersBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public UseConstructorWithTheGreatestNumberOfParametersBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public UseConstructorWithTheGreatestNumberOfParameters build() {
    return new UseConstructorWithTheGreatestNumberOfParameters(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
