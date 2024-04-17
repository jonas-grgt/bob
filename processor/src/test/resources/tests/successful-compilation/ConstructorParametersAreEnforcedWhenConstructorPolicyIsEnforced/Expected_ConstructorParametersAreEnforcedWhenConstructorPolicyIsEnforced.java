package io.jonasg.bob.test.builder;

import io.jonasg.bob.RequiredField;
import io.jonasg.bob.test.ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced;
import java.lang.Integer;
import java.lang.String;

public final class ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder {
  private final RequiredField<String> make = RequiredField.notNullableOfNameWithinType("make", "ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced");

  private final RequiredField<Integer> year = RequiredField.notNullableOfNameWithinType("year", "ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced");

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder() {
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder make(String make) {
    this.make.set(make);
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder year(int year) {
    this.year.set(year);
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder engineSize(
      double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder isElectric(
      boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforcedBuilder fuelEfficiency(
      float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced build() {
    var instance = new ConstructorParametersAreEnforcedWhenConstructorPolicyIsEnforced(make.orElseThrow(), year.orElseThrow());
    instance.setEngineSize(this.engineSize);
    instance.setIsElectric(this.isElectric);
    instance.setFuelEfficiency(this.fuelEfficiency);
    return instance;
  }
}
