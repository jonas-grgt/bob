package io.jonasg.bob.test;

import java.lang.String;

public interface CustomFactoryMethodNameBuilder {
  static CustomFactoryMethodNameBuilder customName() {
    return new DefaultCustomFactoryMethodNameBuilder();
  }

  IsElectricStep engineSize(double engineSize);

  interface BuildStep {
    BuildStep year(int year);

    BuildStep make(String make);

    CustomFactoryMethodName build();
  }

  interface FuelEfficiencyStep {
    BuildStep fuelEfficiency(float fuelEfficiency);
  }

  interface IsElectricStep {
    FuelEfficiencyStep isElectric(boolean isElectric);
  }
}
