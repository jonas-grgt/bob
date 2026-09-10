package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class StaticInheritedFieldExcludedBuilder {
  private String make;

  private String color;

  public StaticInheritedFieldExcludedBuilder() {
  }

  public StaticInheritedFieldExcludedBuilder make(String make) {
    this.make = make;
    return this;
  }

  public StaticInheritedFieldExcludedBuilder color(String color) {
    this.color = color;
    return this;
  }

  public StaticInheritedFieldExcluded build() {
    return new StaticInheritedFieldExcluded(make, color);
  }
}
