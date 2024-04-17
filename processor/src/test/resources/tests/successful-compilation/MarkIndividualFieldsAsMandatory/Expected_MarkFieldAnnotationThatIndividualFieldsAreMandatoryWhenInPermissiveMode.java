package io.jonasg.bob.test.builder;

import io.jonasg.bob.RequiredField;
import io.jonasg.bob.test.MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder {
  private String make;

  private final RequiredField<Integer> year = RequiredField.nullableOfNameWithinType("year", "MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode");

  private double engineSize;

  private final RequiredField<Float> fuelEfficiency = RequiredField.nullableOfNameWithinType("fuelEfficiency", "MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode");

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder() {
  }

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder make(
      String make) {
    this.make = make;
    return this;
  }

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder year(
      int year) {
    this.year.set(year);
    return this;
  }

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode build() {
    var instance = new MarkFieldAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode();
    instance.setMake(this.make);
    instance.setYear(this.year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setFuelEfficiency(this.fuelEfficiency.orElseThrow());
    return instance;
  }
}
