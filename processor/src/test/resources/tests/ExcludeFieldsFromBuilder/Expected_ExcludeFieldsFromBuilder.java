package io.jonasg.bob.test;

import java.lang.String;

public final class ExcludeFieldsFromBuilderBuilder {
  private String make;

  private int year;

  private double engineSize;

  public ExcludeFieldsFromBuilderBuilder() {
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
