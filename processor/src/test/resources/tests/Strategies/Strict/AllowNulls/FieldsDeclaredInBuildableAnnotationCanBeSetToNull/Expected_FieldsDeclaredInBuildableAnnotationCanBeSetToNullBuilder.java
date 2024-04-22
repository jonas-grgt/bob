package io.jonasg.bob.test;

import io.jonasg.bob.RequiredField;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder {
  private final RequiredField<String> make = RequiredField.nullableOfNameWithinType("make", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private final RequiredField<Integer> year = RequiredField.nullableOfNameWithinType("year", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private double engineSize;

  private final RequiredField<Boolean> isElectric = RequiredField.nullableOfNameWithinType("isElectric", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private final RequiredField<Float> fuelEfficiency = RequiredField.nullableOfNameWithinType("fuelEfficiency", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder() {
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public FieldsDeclaredInBuildableAnnotationCanBeSetToNull build() {
    var instance = new FieldsDeclaredInBuildableAnnotationCanBeSetToNull(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric.orElseThrow());
    instance.setFuelEfficiency(this.fuelEfficiency.orElseThrow());
    return instance;
  }
}
