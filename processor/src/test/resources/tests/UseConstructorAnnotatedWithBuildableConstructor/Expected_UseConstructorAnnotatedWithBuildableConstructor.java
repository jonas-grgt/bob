package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class UseConstructorAnnotatedWithBuildableConstructorBuilder {
  private String make;

  private int year;

  public UseConstructorAnnotatedWithBuildableConstructorBuilder() {
  }

  public UseConstructorAnnotatedWithBuildableConstructorBuilder make(String make) {
    this.make = make;
    return this;
  }

  public UseConstructorAnnotatedWithBuildableConstructorBuilder year(int year) {
    this.year = year;
    return this;
  }

  public UseConstructorAnnotatedWithBuildableConstructor build() {
    return new UseConstructorAnnotatedWithBuildableConstructor(make, year);
  }
}
