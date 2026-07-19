package io.jonasg.bob.test;

import java.lang.String;
import java.util.function.Supplier;

public final class WithSupplierDefaultBuilder {
  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  private String make;

  private Supplier<String> uniqueName = WithSupplierDefault.Defaults.uniqueName;

  public WithSupplierDefaultBuilder() {
  }

  public WithSupplierDefaultBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public WithSupplierDefaultBuilder isElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public WithSupplierDefaultBuilder fuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public WithSupplierDefaultBuilder make(String make) {
    this.make = make;
    return this;
  }

  public WithSupplierDefaultBuilder uniqueName(String uniqueName) {
    this.uniqueName = () -> uniqueName;
    return this;
  }

  public WithSupplierDefault build() {
    var instance = new WithSupplierDefault(engineSize, isElectric, fuelEfficiency);
    instance.setMake(this.make);
    instance.setUniqueName(this.uniqueName.get());
    return instance;
  }
}
