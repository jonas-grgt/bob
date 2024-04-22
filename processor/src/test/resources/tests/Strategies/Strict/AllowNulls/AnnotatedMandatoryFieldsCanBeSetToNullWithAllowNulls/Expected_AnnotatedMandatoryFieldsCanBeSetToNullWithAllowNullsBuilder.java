package io.jonasg.bob.test;

import io.jonasg.bob.RequiredField;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder {
  private final RequiredField<String> make = RequiredField.nullableOfNameWithinType("make", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private final RequiredField<Integer> year = RequiredField.nullableOfNameWithinType("year", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private double engineSize;

  private final RequiredField<Boolean> isElectric = RequiredField.nullableOfNameWithinType("isElectric", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private final RequiredField<Float> fuelEfficiency = RequiredField.nullableOfNameWithinType("fuelEfficiency", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder() {
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder isElectric(
      boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls build() {
    var instance = new AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric.orElseThrow());
    instance.setFuelEfficiency(this.fuelEfficiency.orElseThrow());
    return instance;
  }
}
