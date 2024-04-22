package io.jonasg.bob.test;

import io.jonasg.bob.RequiredField;
import java.lang.Integer;
import java.lang.String;

public final class ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder {
  private final RequiredField<String> make = RequiredField.nullableOfNameWithinType("make", "ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls");

  private final RequiredField<Integer> year = RequiredField.nullableOfNameWithinType("year", "ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls");

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder() {
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder make(
      String make) {
    this.make.set(make);
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder year(
      int year) {
    this.year.set(year);
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNullsBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls build() {
    var instance = new ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedAllowNulls(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
