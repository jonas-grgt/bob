package io.jonasg.bob.test;

import java.lang.integer;
import java.lang.String;

public final class DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder implements ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder.YearStep, ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder.BuildStep, ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder {
  private String make;

  private integer year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder() {
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder make(
      String make) {
    this.make = make;
    return this;
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder year(integer year) {
    this.year = year;
    return this;
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls build() {
    var instance = new ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls(make, year);
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
