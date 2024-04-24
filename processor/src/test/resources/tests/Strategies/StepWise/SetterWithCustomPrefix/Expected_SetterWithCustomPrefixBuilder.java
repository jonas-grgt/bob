package io.jonasg.bob.test;

import java.lang.String;

public interface SetterWithCustomPrefixBuilder {
  static SetterWithCustomPrefixBuilder newBuilder() {
    return new DefaultSetterWithCustomPrefixBuilder();
  }

  IsElectricStep withEngineSize(double engineSize);

  interface BuildStep {
    BuildStep withYear(int year);

    BuildStep withMake(String make);

    SetterWithCustomPrefix build();
  }

  interface FuelEfficiencyStep {
    BuildStep withFuelEfficiency(float fuelEfficiency);
  }

  interface IsElectricStep {
    FuelEfficiencyStep withIsElectric(boolean isElectric);
  }
}
