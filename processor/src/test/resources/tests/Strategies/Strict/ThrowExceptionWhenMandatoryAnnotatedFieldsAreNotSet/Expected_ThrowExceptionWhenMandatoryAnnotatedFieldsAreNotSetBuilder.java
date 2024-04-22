package io.jonasg.bob.test;

import io.jonasg.bob.RequiredField;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

public final class ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder {
  private final RequiredField<String> make = RequiredField.notNullableOfNameWithinType("make", "ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet");

  private final RequiredField<Integer> year = RequiredField.notNullableOfNameWithinType("year", "ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet");

  private final RequiredField<Double> engineSize = RequiredField.notNullableOfNameWithinType("engineSize", "ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet");

  private boolean isElectric;

  private float fuelEfficiency;

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder() {
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder engineSize(double engineSize) {
    this.engineSize.set(engineSize);
    return this;
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSetBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet build() {
    var instance = new ThrowExceptionWhenMandatoryAnnotatedFieldsAreNotSet(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize.orElseThrow());
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
