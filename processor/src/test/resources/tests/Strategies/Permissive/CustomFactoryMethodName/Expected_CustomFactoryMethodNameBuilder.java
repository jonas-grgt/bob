package io.jonasg.bob.test;

import java.lang.String;

public final class CustomFactoryMethodNameBuilder {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private int year;

  public CustomFactoryMethodNameBuilder() {
  }

  public CustomFactoryMethodNameBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public CustomFactoryMethodNameBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public CustomFactoryMethodNameBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public CustomFactoryMethodNameBuilder make(String make) {
    this.make = make;
    return this;
  }

  public CustomFactoryMethodNameBuilder year(int year) {
    this.year = year;
    return this;
  }

  public CustomFactoryMethodName build() {
    var instance = new CustomFactoryMethodName(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }

  public static CustomFactoryMethodNameBuilder customName() {
    return new CustomFactoryMethodNameBuilder();
  }
}
