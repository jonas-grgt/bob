package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class SubclassConstructorTakesInheritedFieldsBuilder {
  private String make;

  private String color;

  public SubclassConstructorTakesInheritedFieldsBuilder() {
  }

  public SubclassConstructorTakesInheritedFieldsBuilder make(String make) {
    this.make = make;
    return this;
  }

  public SubclassConstructorTakesInheritedFieldsBuilder color(String color) {
    this.color = color;
    return this;
  }

  public SubclassConstructorTakesInheritedFields build() {
    return new SubclassConstructorTakesInheritedFields(make, color);
  }
}
