package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class JSpecifyNullMarkedClassWithAllowNullsBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "JSpecifyNullMarkedClassWithAllowNulls");

  private final ValidatableField<String> model = ValidatableField.ofNullableField("model", "JSpecifyNullMarkedClassWithAllowNulls");

  public JSpecifyNullMarkedClassWithAllowNullsBuilder() {
  }

  public JSpecifyNullMarkedClassWithAllowNullsBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public JSpecifyNullMarkedClassWithAllowNullsBuilder model(String model) {
    this.model.set(model);
    return this;
  }

  public JSpecifyNullMarkedClassWithAllowNulls build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!model.isValid()) missingFields.add("model");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "JSpecifyNullMarkedClassWithAllowNulls");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "JSpecifyNullMarkedClassWithAllowNulls")).toList());
    }
    return new JSpecifyNullMarkedClassWithAllowNulls(make.get(), model.get());
  }
}
