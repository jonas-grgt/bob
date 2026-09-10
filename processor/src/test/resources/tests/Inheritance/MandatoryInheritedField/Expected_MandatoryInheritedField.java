package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class MandatoryInheritedFieldBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "MandatoryInheritedField");

  private String color;

  public MandatoryInheritedFieldBuilder() {
  }

  public MandatoryInheritedFieldBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public MandatoryInheritedFieldBuilder color(String color) {
    this.color = color;
    return this;
  }

  public MandatoryInheritedField build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "MandatoryInheritedField");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "MandatoryInheritedField")).toList());
    }
    return new MandatoryInheritedField(make.get(), color);
  }
}
