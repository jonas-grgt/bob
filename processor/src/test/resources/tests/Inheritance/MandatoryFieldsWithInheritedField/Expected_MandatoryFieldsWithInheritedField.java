package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class MandatoryFieldsWithInheritedFieldBuilder {
  private String color;

  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "MandatoryFieldsWithInheritedField");

  public MandatoryFieldsWithInheritedFieldBuilder() {
  }

  public MandatoryFieldsWithInheritedFieldBuilder color(String color) {
    this.color = color;
    return this;
  }

  public MandatoryFieldsWithInheritedFieldBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public MandatoryFieldsWithInheritedField build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "MandatoryFieldsWithInheritedField");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "MandatoryFieldsWithInheritedField")).toList());
    }
    var instance = new MandatoryFieldsWithInheritedField();
    instance.setColor(this.color);
    instance.setMake(this.make.get());
    return instance;
  }
}
