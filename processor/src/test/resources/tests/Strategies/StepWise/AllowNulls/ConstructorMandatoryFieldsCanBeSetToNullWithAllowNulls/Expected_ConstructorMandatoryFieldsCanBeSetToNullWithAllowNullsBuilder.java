package io.jonasg.bob.test;

import java.lang.integer;
import java.lang.String;

public interface ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder {
  static ConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder newBuilder() {
    return new DefaultConstructorMandatoryFieldsCanBeSetToNullWithAllowNullsBuilder();
  }

  YearStep make(String make);

  interface BuildStep {
    BuildStep fuelEfficiency(float fuelEfficiency);

    BuildStep isElectric(boolean isElectric);

    BuildStep engineSize(double engineSize);

    ConstructorMandatoryFieldsCanBeSetToNullWithAllowNulls build();
  }

  interface YearStep {
    BuildStep year(integer year);
  }
}
