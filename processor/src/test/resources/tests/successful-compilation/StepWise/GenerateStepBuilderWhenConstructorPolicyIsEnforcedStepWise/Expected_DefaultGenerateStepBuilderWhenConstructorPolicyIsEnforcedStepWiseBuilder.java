package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise;
import java.lang.String;

public final class DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder implements GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder.YearStep, GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder.BuildStep, GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder() {
  }

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder make(
      String make) {
    this.make = make;
    return this;
  }

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder year(int year) {
    this.year = year;
    return this;
  }

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultGenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWiseBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise build() {
    var instance = new GenerateStepBuilderWhenConstructorPolicyIsEnforcedStepWise(make, year);
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
