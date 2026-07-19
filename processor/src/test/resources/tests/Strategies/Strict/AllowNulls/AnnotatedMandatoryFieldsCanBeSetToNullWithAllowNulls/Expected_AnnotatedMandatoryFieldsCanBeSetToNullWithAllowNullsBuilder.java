package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

public final class AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNullableField("make", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private final ValidatableField<Integer> year = ValidatableField.ofNullableField("year", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private double engineSize;

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNullableField("isElectric", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNullableField("fuelEfficiency", "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");

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
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!year.isValid()) missingFields.add("year");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls")).toList());
    }
    var instance = new AnnotatedMandatoryFieldsCanBeSetToNullWithAllowNulls(make.get(), year.get());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric.get());
    instance.setFuelEfficiency(this.fuelEfficiency.get());
    return instance;
  }
}
