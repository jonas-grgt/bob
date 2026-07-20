package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public interface OptionalFieldInStepWiseBuilder {
  static OptionalFieldInStepWiseBuilder newBuilder() {
    return new DefaultOptionalFieldInStepWiseBuilder();
  }

  ColorStep make(String make);

  interface BuildStep {
    BuildStep year(int year);

    OptionalFieldInStepWise build();
  }

  interface ColorStep {
    BuildStep color(String color);
  }
}
