package io.jonasg.bob.test;

import io.jonasg.bob.ValidatableField;
import java.lang.String;

public final class OptionalConstructorFieldInStrictStrategyBuilder {
  private final ValidatableField<String> make = ValidatableField.ofNoneNullableField("make", "OptionalConstructorFieldInStrictStrategy");

  private int year;

  public OptionalConstructorFieldInStrictStrategyBuilder() {
  }

  public OptionalConstructorFieldInStrictStrategyBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public OptionalConstructorFieldInStrictStrategyBuilder year(int year) {
    this.year = year;
    return this;
  }

  public OptionalConstructorFieldInStrictStrategy build() {
    return new OptionalConstructorFieldInStrictStrategy(make.orElseThrow(), year);
  }
}
