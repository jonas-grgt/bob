package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class GenericInheritedFieldBuilder {
  private String name;

  private String color;

  public GenericInheritedFieldBuilder() {
  }

  public GenericInheritedFieldBuilder name(String name) {
    this.name = name;
    return this;
  }

  public GenericInheritedFieldBuilder color(String color) {
    this.color = color;
    return this;
  }

  public GenericInheritedField build() {
    return new GenericInheritedField(name, color);
  }
}
