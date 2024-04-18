package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise;
import java.lang.String;

public interface GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder {
  static GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder newBuilder() {
    return new DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder();
  }

  YearStep make(String make);

  interface BuildStep {
    BuildStep fuelEfficiency(float fuelEfficiency);

    BuildStep isElectric(boolean isElectric);

    BuildStep engineSize(double engineSize);

    GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise build();
  }

  interface YearStep {
    BuildStep year(int year);
  }
}
