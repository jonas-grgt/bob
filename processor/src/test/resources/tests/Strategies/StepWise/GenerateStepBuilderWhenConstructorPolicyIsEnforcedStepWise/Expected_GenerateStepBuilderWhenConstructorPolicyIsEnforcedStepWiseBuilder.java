package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
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
