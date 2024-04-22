package io.jonasg.bob.test;

public final class UseFirstConstructorWithTheMostNumberOfParametersBuilder {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public UseFirstConstructorWithTheMostNumberOfParametersBuilder() {
  }

  public UseFirstConstructorWithTheMostNumberOfParametersBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public UseFirstConstructorWithTheMostNumberOfParametersBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public UseFirstConstructorWithTheMostNumberOfParametersBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public UseFirstConstructorWithTheMostNumberOfParameters build() {
    return new UseFirstConstructorWithTheMostNumberOfParameters(engineSize, isElectric, fuelEfficiency);
  }
}
