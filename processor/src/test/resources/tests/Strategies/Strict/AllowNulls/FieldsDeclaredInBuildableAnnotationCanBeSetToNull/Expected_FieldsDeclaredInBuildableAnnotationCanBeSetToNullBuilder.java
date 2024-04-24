package io.jonasg.bob.test;

import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class FieldsDeclaredInBuildableAnnotationCanBeSetToNullBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNullableField("make", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private final ValidatableField<Integer> year = ValidatableField.ofNullableField("year", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private double engineSize;

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNullableField("isElectric", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNullableField("fuelEfficiency", "FieldsDeclaredInBuildableAnnotationCanBeSetToNull");

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
