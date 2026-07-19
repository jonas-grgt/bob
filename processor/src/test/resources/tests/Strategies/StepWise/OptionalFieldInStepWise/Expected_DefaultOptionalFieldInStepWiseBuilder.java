package io.jonasg.bob.test;

import java.lang.String;

public final class DefaultOptionalFieldInStepWiseBuilder implements OptionalFieldInStepWiseBuilder.ColorStep, OptionalFieldInStepWiseBuilder, OptionalFieldInStepWiseBuilder.BuildStep {
  private String make;

  private int year;

  private String color;

  public DefaultOptionalFieldInStepWiseBuilder() {
  }

  public DefaultOptionalFieldInStepWiseBuilder make(String make) {
    this.make = make;
    return this;
  }

  public DefaultOptionalFieldInStepWiseBuilder year(int year) {
    this.year = year;
    return this;
  }

  public DefaultOptionalFieldInStepWiseBuilder color(String color) {
    this.color = color;
    return this;
  }

  public OptionalFieldInStepWise build() {
    return new OptionalFieldInStepWise(make, year, color);
  }
}
