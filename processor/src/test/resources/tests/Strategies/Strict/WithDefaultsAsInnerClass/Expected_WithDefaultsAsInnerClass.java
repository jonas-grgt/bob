package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Float;
import java.lang.String;

public final class WithDefaultsAsInnerClassBuilder {
  private int year = WithDefaultsAsInnerClass.Defaults.year;

  private final ValidatableField<Double> engineSize = ValidatableField.ofNoneNullableField("engineSize", "WithDefaultsAsInnerClass");

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "WithDefaultsAsInnerClass");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "WithDefaultsAsInnerClass");

  private String make;

  public WithDefaultsAsInnerClassBuilder() {
  }

  public WithDefaultsAsInnerClassBuilder year(int year) {
    this.year = year;
    return this;
  }

  public WithDefaultsAsInnerClassBuilder engineSize(double engineSize) {
    this.engineSize.set(engineSize);
    return this;
  }

  public WithDefaultsAsInnerClassBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public WithDefaultsAsInnerClassBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public WithDefaultsAsInnerClassBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithDefaultsAsInnerClass build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!engineSize.isValid()) missingFields.add("engineSize");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithDefaultsAsInnerClass");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithDefaultsAsInnerClass")).toList());
    }
    var instance = new WithDefaultsAsInnerClass(year, engineSize.get(), isElectric.get(), fuelEfficiency.get());
    instance.setMake(this.make);
    return instance;
  }
}
