package io.jonasg.bob.test.builder;

import io.jonasg.bob.RequiredField;
import io.jonasg.bob.test.MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder {
  private String make;

  private final RequiredField<Integer> year = RequiredField.nullableOfNameWithinType("year", "MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode");

  private double engineSize;

  private final RequiredField<Float> fuelEfficiency = RequiredField.nullableOfNameWithinType("fuelEfficiency", "MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode");

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder(
      ) {
  }

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder make(
      String make) {
    this.make = make;
    return this;
  }

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder year(
      int year) {
    this.year.set(year);
    return this;
  }

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveModeBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode build() {
    var instance = new MarkThroughTopLevelAnnotationThatIndividualFieldsAreMandatoryWhenInPermissiveMode();
    instance.setMake(this.make);
    instance.setYear(this.year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setFuelEfficiency(this.fuelEfficiency.orElseThrow());
    return instance;
  }
}
