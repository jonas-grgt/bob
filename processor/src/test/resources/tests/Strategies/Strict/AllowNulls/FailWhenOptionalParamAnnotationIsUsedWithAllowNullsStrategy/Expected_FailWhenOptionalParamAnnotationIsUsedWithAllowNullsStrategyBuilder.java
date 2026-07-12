package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNullableField("make", "FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy");

  private int year;

  public FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategyBuilder() {
    TestDefaultsResolver.applyDefaults(this, FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy.class);
  }

  public FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy")).toList());
    }
    return new FailWhenOptionalParamAnnotationIsUsedWithAllowNullsStrategy(make.get(), year);
  }
}
