package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public interface StepWiseInheritedFieldsBuilder {
  static StepWiseInheritedFieldsBuilder newBuilder() {
    return new DefaultStepWiseInheritedFieldsBuilder();
  }

  ColorStep make(String make);

  interface BuildStep {
    StepWiseInheritedFields build();
  }

  interface ColorStep {
    BuildStep color(String color);
  }
}
