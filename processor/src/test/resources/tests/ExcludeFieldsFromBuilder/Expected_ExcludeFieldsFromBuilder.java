package io.jonasg.bob.test;

import java.lang.ReflectiveOperationException;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class ExcludeFieldsFromBuilderBuilder {
  private String make;

  private int year;

  private double engineSize;

  public ExcludeFieldsFromBuilderBuilder() {
    try {
      Class<?> resolverClass = Class.forName("io.jonasg.bob.DefaultsResolver");
      java.lang.reflect.Method method = resolverClass.getMethod("applyDefaults", Object.class, Class.class);
      method.invoke(null, this, ExcludeFieldsFromBuilder.class);
    } catch (ReflectiveOperationException ignored) {
    }
  }

  public ExcludeFieldsFromBuilderBuilder make(String make) {
    this.make = make;
    return this;
  }

  public ExcludeFieldsFromBuilderBuilder engineSize(double engineSize) {
    this.engineSize = engineSize;
    return this;
  }

  public ExcludeFieldsFromBuilder build() {
    return new ExcludeFieldsFromBuilder(make, year, engineSize);
  }
}
