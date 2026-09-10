package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class InheritedFieldWithDefaultsBuilder {
  private String color;

  private String make = InheritedFieldDefaults.make;

  public InheritedFieldWithDefaultsBuilder() {
  }

  public InheritedFieldWithDefaultsBuilder color(String color) {
    this.color = color;
    return this;
  }

  public InheritedFieldWithDefaultsBuilder make(String make) {
    this.make = make;
    return this;
  }

  public InheritedFieldWithDefaults build() {
    var instance = new InheritedFieldWithDefaults();
    instance.setColor(this.color);
    instance.setMake(this.make);
    return instance;
  }
}
