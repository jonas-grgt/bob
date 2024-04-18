package io.jonasg.bob.test.builder;

import io.jonasg.bob.test.GenerateStepBuilderWithSingleArgumentConstructor;
import java.lang.String;

public final class DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder implements GenerateStepBuilderWithSingleArgumentConstructorBuilder.BuildStep, GenerateStepBuilderWithSingleArgumentConstructorBuilder {
  private int year;

  private String make;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder() {
  }

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder year(int year) {
    this.year = year;
    return this;
  }

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder make(String make) {
    this.make = make;
    return this;
  }

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public DefaultGenerateStepBuilderWithSingleArgumentConstructorBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public GenerateStepBuilderWithSingleArgumentConstructor build() {
    var instance = new GenerateStepBuilderWithSingleArgumentConstructor(year);
    instance.setMake(this.make);
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
