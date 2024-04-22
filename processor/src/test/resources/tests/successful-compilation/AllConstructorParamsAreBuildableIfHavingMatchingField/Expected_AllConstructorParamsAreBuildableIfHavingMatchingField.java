package io.jonasg.bob.test;

import java.lang.String;

public final class AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder {
  private String make;

  private double engineSize;

  private boolean isElectric;

  public AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder() {
  }

  public AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public AllConstructorParamsAreBuildableIfHavingMatchingFieldBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public AllConstructorParamsAreBuildableIfHavingMatchingField build() {
    return new AllConstructorParamsAreBuildableIfHavingMatchingField(make, null, engineSize, isElectric, null);
  }
}
