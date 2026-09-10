package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class DefaultStepWiseInheritedFieldsBuilder implements StepWiseInheritedFieldsBuilder, StepWiseInheritedFieldsBuilder.BuildStep, StepWiseInheritedFieldsBuilder.ColorStep {
  private String make;

  private String color;

  public DefaultStepWiseInheritedFieldsBuilder() {
  }

  public DefaultStepWiseInheritedFieldsBuilder make(String make) {
    this.make = make;
    return this;
  }

  public DefaultStepWiseInheritedFieldsBuilder color(String color) {
    this.color = color;
    return this;
  }

  public StepWiseInheritedFields build() {
    return new StepWiseInheritedFields(make, color);
  }
}
