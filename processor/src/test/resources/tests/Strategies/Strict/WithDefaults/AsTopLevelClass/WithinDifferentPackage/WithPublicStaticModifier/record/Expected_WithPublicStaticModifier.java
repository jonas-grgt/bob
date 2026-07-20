package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import io.jonasg.bob.test.foo.bar.DefaultsClass;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class WithPublicStaticModifierBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "WithPublicStaticModifier");

  private int year = DefaultsClass.year;

  private double engineSize = DefaultsClass.engineSize;

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "WithPublicStaticModifier");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "WithPublicStaticModifier");

  public WithPublicStaticModifierBuilder() {
  }

  public WithPublicStaticModifierBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public WithPublicStaticModifierBuilder year(int year) {
    this.year = year;
    return this;
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

  public WithPublicStaticModifier build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithPublicStaticModifier");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithPublicStaticModifier")).toList());
    }
    return new WithPublicStaticModifier(make.get(), year, engineSize, isElectric.get(), fuelEfficiency.get());
  }
}
