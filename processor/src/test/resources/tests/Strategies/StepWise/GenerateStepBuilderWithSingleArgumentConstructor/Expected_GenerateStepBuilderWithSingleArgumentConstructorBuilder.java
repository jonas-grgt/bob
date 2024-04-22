package io.jonasg.bob.test;

import java.lang.String;

public interface GenerateStepBuilderWithSingleArgumentConstructorBuilder {
  static GenerateStepBuilderWithSingleArgumentConstructorBuilder newBuilder() {
    return new DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder();
  }

  BuildStep year(int year);

  interface BuildStep {
    BuildStep fuelEfficiency(float fuelEfficiency);

    BuildStep isElectric(boolean isElectric);

    BuildStep engineSize(double engineSize);

    BuildStep make(String make);

    GenerateStepBuilderWithSingleArgumentConstructor build();
  }
}
