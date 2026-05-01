package io.jonasg.bob.test;

import java.lang.String;

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
