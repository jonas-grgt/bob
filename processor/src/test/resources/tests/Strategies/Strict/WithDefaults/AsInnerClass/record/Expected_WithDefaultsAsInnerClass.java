package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Float;
import java.lang.String;

public final class WithDefaultsAsInnerClassBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "WithDefaultsAsInnerClass");

  private int year = WithDefaultsAsInnerClass.Defaults.year;

  private final ValidatableField<Double> engineSize = ValidatableField.ofNoneNullableField("engineSize", "WithDefaultsAsInnerClass");

  private final ValidatableField<Boolean> isElectric = ValidatableField.ofNoneNullableField("isElectric", "WithDefaultsAsInnerClass");

  private final ValidatableField<Float> fuelEfficiency = ValidatableField.ofNoneNullableField("fuelEfficiency", "WithDefaultsAsInnerClass");

  public WithDefaultsAsInnerClassBuilder() {
    TestDefaultsResolver.applyDefaults(this, WithDefaultsAsInnerClass.class);
  }

  public WithDefaultsAsInnerClassBuilder make(String make) {
    this.make.set(make);
    return this;
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

  public WithDefaultsAsInnerClass build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!engineSize.isValid()) missingFields.add("engineSize");
    if (!isElectric.isValid()) missingFields.add("isElectric");
    if (!fuelEfficiency.isValid()) missingFields.add("fuelEfficiency");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithDefaultsAsInnerClass");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithDefaultsAsInnerClass")).toList());
    }
    return new WithDefaultsAsInnerClass(make.get(), year, engineSize.get(), isElectric.get(), fuelEfficiency.get());
  }
}
