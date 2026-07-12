package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNullableField("make", "FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy");

  private int year;

  public FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategyBuilder() {
    TestDefaultsResolver.applyDefaults(this, FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy.class);
  }

  public FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy")).toList());
    }
    return new FailWhenOptionalAnnotationIsUsedWithAllowNullsStrategy(make.get(), year);
  }
}
