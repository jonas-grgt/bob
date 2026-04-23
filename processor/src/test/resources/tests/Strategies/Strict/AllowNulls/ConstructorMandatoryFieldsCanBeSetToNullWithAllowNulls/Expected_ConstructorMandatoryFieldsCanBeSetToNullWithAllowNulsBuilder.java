package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Integer;
import java.lang.String;

public final class ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNullableField("make", "ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private final ValidatableField<Integer> year = ValidatableField.ofNullableField("year", "ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls");

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder() {
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!year.isValid()) missingFields.add("year");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls")).toList());
    }
    var instance = new ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls(make.get(), year.get());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
