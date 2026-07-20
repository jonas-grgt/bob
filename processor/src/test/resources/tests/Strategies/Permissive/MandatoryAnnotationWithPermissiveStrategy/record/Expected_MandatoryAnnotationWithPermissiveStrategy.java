package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Double;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class MandatoryAnnotationWithPermissiveStrategyBuilder {
  private String make;

  private int year;

  private final ValidatableField<Double> engineSize = ValidatableField.ofNoneNullableField("engineSize", "MandatoryAnnotationWithPermissiveStrategy");

  private boolean isElectric;

  private float fuelEfficiency;

  public MandatoryAnnotationWithPermissiveStrategyBuilder() {
  }

  public MandatoryAnnotationWithPermissiveStrategyBuilder make(String make) {
    this.make = make;
    return this;
  }

  public MandatoryAnnotationWithPermissiveStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public MandatoryAnnotationWithPermissiveStrategyBuilder engineSize(double engineSize) {
    this.engineSize.set(engineSize);
    return this;
  }

  public MandatoryAnnotationWithPermissiveStrategyBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public MandatoryAnnotationWithPermissiveStrategyBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public MandatoryAnnotationWithPermissiveStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!engineSize.isValid()) missingFields.add("engineSize");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "MandatoryAnnotationWithPermissiveStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "MandatoryAnnotationWithPermissiveStrategy")).toList());
    }
    return new MandatoryAnnotationWithPermissiveStrategy(make, year, engineSize.get(), isElectric, fuelEfficiency);
  }
}
