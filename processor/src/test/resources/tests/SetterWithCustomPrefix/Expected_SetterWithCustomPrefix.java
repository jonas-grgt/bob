package io.jonasg.bob.test;

import java.lang.ReflectiveOperationException;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class SetterWithCustomPrefixBuilder {
  private String make;

  private int year;

  private double engineSize;

  private boolean isElectric;

  private float fuelEfficiency;

  public SetterWithCustomPrefixBuilder() {
    try {
      Class<?> resolverClass = Class.forName("io.jonasg.bob.DefaultsResolver");
      java.lang.reflect.Method method = resolverClass.getMethod("applyDefaults", Object.class, Class.class, String.class);
      method.invoke(null, this, SetterWithCustomPrefix.class, "with");
    } catch (ReflectiveOperationException ignored) {
    }
  }

  public SetterWithCustomPrefixBuilder withMake(String make) {
    this.make = make;
    return this;
  }

  public SetterWithCustomPrefixBuilder withYear(int year) {
    this.year = year;
    return this;
  }

  public SetterWithCustomPrefixBuilder withEngineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public SetterWithCustomPrefixBuilder withIsElectric(boolean isElectric) {
    this.isElectric = isElectric;
    return this;
  }

  public SetterWithCustomPrefixBuilder withFuelEfficiency(float fuelEfficiency) {
    this.fuelEfficiency = fuelEfficiency;
    return this;
  }

  public SetterWithCustomPrefix build() {
    return new SetterWithCustomPrefix(make, year, engineSize, isElectric, fuelEfficiency);
  }
}
