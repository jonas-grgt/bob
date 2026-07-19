package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class OptionalConstructorParamInStrictStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "OptionalConstructorParamInStrictStrategy");

  private int year;

  public OptionalConstructorParamInStrictStrategyBuilder() {
  }

  public OptionalConstructorParamInStrictStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public OptionalConstructorParamInStrictStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public OptionalConstructorParamInStrictStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "OptionalConstructorParamInStrictStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "OptionalConstructorParamInStrictStrategy")).toList());
    }
    return new OptionalConstructorParamInStrictStrategy(make.get(), year);
  }
}
