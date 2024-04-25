package io.jonasg.bob.test;

import java.lang.String;

public final class DefaultCustomFactoryMethodNameBuilder implements CustomFactoryMethodNameBuilder.BuildStep, CustomFactoryMethodNameBuilder, CustomFactoryMethodNameBuilder.FuelEfficiencyStep, CustomFactoryMethodNameBuilder.IsElectricStep {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private int year;

  public DefaultCustomFactoryMethodNameBuilder() {
  }

  public DefaultCustomFactoryMethodNameBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultCustomFactoryMethodNameBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultCustomFactoryMethodNameBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public DefaultCustomFactoryMethodNameBuilder make(String make) {
    this.make = make;
    return this;
  }

  public DefaultCustomFactoryMethodNameBuilder year(int year) {
    this.year = year;
    return this;
  }

  public CustomFactoryMethodName build() {
    var instance = new CustomFactoryMethodName(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
