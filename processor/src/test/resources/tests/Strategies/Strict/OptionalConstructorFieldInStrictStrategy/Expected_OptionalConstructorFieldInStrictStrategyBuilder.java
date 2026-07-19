package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class OptionalConstructorFieldInStrictStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "OptionalConstructorFieldInStrictStrategy");

  private int year;

  public OptionalConstructorFieldInStrictStrategyBuilder() {
  }

  public OptionalConstructorFieldInStrictStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public OptionalConstructorFieldInStrictStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public OptionalConstructorFieldInStrictStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "OptionalConstructorFieldInStrictStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "OptionalConstructorFieldInStrictStrategy")).toList());
    }
    return new OptionalConstructorFieldInStrictStrategy(make.get(), year);
  }
}
