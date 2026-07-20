package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class WithPackagePrivateModifierBuilder {
  private double engineSize = DefaultsClass.engineSize;

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "WithPackagePrivateModifier");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "WithPackagePrivateModifier");

  private String make;

  private int year = DefaultsClass.year;

  public WithPackagePrivateModifierBuilder() {
  }

  public WithPackagePrivateModifierBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public WithPackagePrivateModifierBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public WithPackagePrivateModifierBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public WithPackagePrivateModifierBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithPackagePrivateModifierBuilder year(int year) {
    this.year = year;
    return this;
  }

  public WithPackagePrivateModifier build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithPackagePrivateModifier");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithPackagePrivateModifier")).toList());
    }
    var instance = new WithPackagePrivateModifier(engineSize, isElectric.get(), fuelEfficiency.get());
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
