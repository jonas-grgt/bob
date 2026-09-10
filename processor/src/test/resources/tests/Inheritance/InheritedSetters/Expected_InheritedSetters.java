package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class InheritedSettersBuilder {
  private String color;

  private String make;

  public InheritedSettersBuilder() {
  }

  public InheritedSettersBuilder color(String color) {
    this.color = color;
    return this;
  }

  public InheritedSettersBuilder make(String make) {
    this.make = make;
    return this;
  }

  public InheritedSetters build() {
    var instance = new InheritedSetters();
    instance.setColor(this.color);
    instance.setMake(this.make);
    return instance;
  }
}
