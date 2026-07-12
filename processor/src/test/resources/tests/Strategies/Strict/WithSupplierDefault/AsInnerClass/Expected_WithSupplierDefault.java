package io.jonasg.bob.test;

import io.jonasg.bob.MandatoryFieldMissingException;
import io.jonasg.bob.MandatoryFieldsMissingException;
import io.jonasg.bob.MissingField;
import io.jonasg.bob.TestDefaultsResolver;
import io.jonasg.bob.ValidatableField;
import java.lang.String;
import java.util.function.Supplier;

public final class WithSupplierDefaultBuilder {
  private Supplier<String> uniqueName = WithSupplierDefault.Defaults.uniqueName;

  private final ValidatableField<String> other = ValidatableField.ofNoneNullableField("other", "WithSupplierDefault");

  public WithSupplierDefaultBuilder() {
    TestDefaultsResolver.applyDefaults(this, WithSupplierDefault.class);
  }

  public WithSupplierDefaultBuilder uniqueName(String uniqueName) {
    this.uniqueName = () -> uniqueName;
    return this;
  }

  public WithSupplierDefaultBuilder other(String other) {
    this.other.set(other);
    return this;
  }

  public WithSupplierDefault build() {
    var missingFields = new java.util.ArrayList<String>();
    if (!other.isValid()) missingFields.add("other");
    if (missingFields.size() == 1) {
      throw new MandatoryFieldMissingException(missingFields.get(0), "WithSupplierDefault");
    } else if (!missingFields.isEmpty()) {
      throw new MandatoryFieldsMissingException(missingFields.stream().map(f -> new MissingField(f, "WithSupplierDefault")).toList());
    }
    return new WithSupplierDefault(uniqueName.get(), other.get());
  }
}
