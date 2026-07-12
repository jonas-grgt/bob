package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import io.jonasg.bob.test.foo.bar.DefaultsClass;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;

public final class WithPublicStaticModifierBuilder {
  private double engineSize = DefaultsClass.engineSize;

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "WithPublicStaticModifier");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "WithPublicStaticModifier");

  private String make;

  private int year = DefaultsClass.year;

  public WithPublicStaticModifierBuilder() {
    TestDefaultsResolver.applyDefaults(this, WithPublicStaticModifier.class);
  }

  public WithPublicStaticModifierBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public WithPublicStaticModifierBuilder isElectric(boolean isElectric) {
    this.isElectric.set(isElectric);
    return this;
  }

  public WithPublicStaticModifierBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency.set(fuelEfficiency);
    return this;
  }

  public WithPublicStaticModifierBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithPublicStaticModifierBuilder year(int year) {
    this.year = year;
    return this;
  }

  public WithPublicStaticModifier build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithPublicStaticModifier");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithPublicStaticModifier")).toList());
    }
    var instance = new WithPublicStaticModifier(engineSize, isElectric.get(), fuelEfficiency.get());
    instance.setMake(this.make);
    instance.setYear(this.year);
    return instance;
  }
}
