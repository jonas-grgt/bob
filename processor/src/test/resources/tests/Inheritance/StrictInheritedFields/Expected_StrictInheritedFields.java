package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class StrictInheritedFieldsBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "StrictInheritedFields");

  private final ValidatableField<String> color = ValidatableField.ofNoneNullableField("color", "StrictInheritedFields");

  public StrictInheritedFieldsBuilder() {
  }

  public StrictInheritedFieldsBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public StrictInheritedFieldsBuilder color(String color) {
    this.color.set(color);
    return this;
  }

  public StrictInheritedFields build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (!color.isValid()) missingFields.add("color");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "StrictInheritedFields");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "StrictInheritedFields")).toList());
    }
    return new StrictInheritedFields(make.get(), color.get());
  }
}
