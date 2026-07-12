package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class MandatoryAnnotatedFieldDeclarationBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "MandatoryAnnotatedFieldDeclaration");

  private String model;

  public MandatoryAnnotatedFieldDeclarationBuilder() {
    TestDefaultsResolver.applyDefaults(this, MandatoryAnnotatedFieldDeclaration.class);
  }

  public MandatoryAnnotatedFieldDeclarationBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public MandatoryAnnotatedFieldDeclarationBuilder model(String model) {
    this.model = model;
    return this;
  }

  public MandatoryAnnotatedFieldDeclaration build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!make.isValid()) missingFields.add("make");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "MandatoryAnnotatedFieldDeclaration");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "MandatoryAnnotatedFieldDeclaration")).toList());
    }
    var instance = new MandatoryAnnotatedFieldDeclaration();
    instance.setMake(this.make.get());
    instance.setModel(this.model);
    return instance;
  }
}
