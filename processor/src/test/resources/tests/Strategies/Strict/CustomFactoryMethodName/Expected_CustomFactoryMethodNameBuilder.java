package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Float;
import java.lang.String;

public final class CustomFactoryMethodNameBuilder {
  private final ValidatableField<Double> engineSize = ValidatableField.ofNoneNullableField("engineSize", "CustomFactoryMethodName");

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "CustomFactoryMethodName");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "CustomFactoryMethodName");

  private String make;

  private int year;

  public CustomFactoryMethodNameBuilder() {
  }

  public CustomFactoryMethodNameBuilder engineSize(double engineSize) {
    this.engineSize.set(engineSize);
    return this;
  }

  public CustomFactoryMethodNameBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public CustomFactoryMethodNameBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public CustomFactoryMethodNameBuilder make(String make) {
    this.make = make;
    return this;
  }

  public CustomFactoryMethodNameBuilder year(int year) {
    this.year = year;
    return this;
  }

  public CustomFactoryMethodName build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!engineSize.isValid()) missingFields.add("engineSize");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "CustomFactoryMethodName");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "CustomFactoryMethodName")).toList());
    }
    var instance = new CustomFactoryMethodName(engineSize.get(), isElectric.get(), fuelEfficiency.get());
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }

  public static CustomFactoryMethodNameBuilder customName() {
    return new CustomFactoryMethodNameBuilder();
  }
}
