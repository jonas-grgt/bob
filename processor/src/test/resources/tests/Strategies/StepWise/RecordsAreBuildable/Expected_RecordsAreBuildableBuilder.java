package io.jonasg.bob.test;

import java.lang.String;

public interface RecordsAreBuildableBuilder {
  static RecordsAreBuildableBuilder newBuilder() {
    return new DefaultRecordsAreBuildableBuilder();
  }

  YearStep make(String make);

  interface BuildStep {
    RecordsAreBuildable build();
  }

  interface FuelEfficiencyStep {
    BuildStep fuelEfficiency(float fuelEfficiency);
  }

  interface IsElectricStep {
    FuelEfficiencyStep isElectric(boolean isElectric);
  }

  interface EngineSizeStep {
    IsElectricStep engineSize(double engineSize);
  }

  interface YearStep {
    EngineSizeStep year(int year);
  }
}
