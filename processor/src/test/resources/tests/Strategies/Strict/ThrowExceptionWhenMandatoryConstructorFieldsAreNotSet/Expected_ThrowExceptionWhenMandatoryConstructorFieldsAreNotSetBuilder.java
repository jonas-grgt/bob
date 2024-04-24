package io.jonasg.bob.test;

import io.jonasg.bob.ValidatableField;
import java.lang.Integer;
import java.lang.String;

public final class ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet");

  private final ValidatableField<Integer> year = ValidatableField.ofNoneNullableField("year", "ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet");

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder() {
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSetBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet build() {
    var instance = new ThrowExceptionWhenMandatoryConstructorFieldsAreNotSet(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
