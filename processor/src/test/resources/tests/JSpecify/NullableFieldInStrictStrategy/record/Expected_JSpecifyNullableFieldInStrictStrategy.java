package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class JSpecifyNullableFieldInStrictStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "JSpecifyNullableFieldInStrictStrategy");

  private final ValidatableField<String> model = ValidatableField.ofNullableField("model", "JSpecifyNullableFieldInStrictStrategy");

  public JSpecifyNullableFieldInStrictStrategyBuilder() {
  }

  public JSpecifyNullableFieldInStrictStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public JSpecifyNullableFieldInStrictStrategyBuilder model(String model) {
    this.model.set(model);
    return this;
  }

  public JSpecifyNullableFieldInStrictStrategy build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!model.isValid()) missingFields.add("model");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "JSpecifyNullableFieldInStrictStrategy");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "JSpecifyNullableFieldInStrictStrategy")).toList());
    }
    return new JSpecifyNullableFieldInStrictStrategy(make.get(), model.get());
  }
}
