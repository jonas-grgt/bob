package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class AccessibleInheritedSettersBuilder {
  private String color;

  private String make;

  private String model;

  public AccessibleInheritedSettersBuilder() {
  }

  public AccessibleInheritedSettersBuilder color(String color) {
    this.color = color;
    return this;
  }

  public AccessibleInheritedSettersBuilder make(String make) {
    this.make = make;
    return this;
  }

  public AccessibleInheritedSettersBuilder model(String model) {
    this.model = model;
    return this;
  }

  public AccessibleInheritedSetters build() {
    var instance = new AccessibleInheritedSetters();
    instance.setColor(this.color);
    instance.setMake(this.make);
    instance.setModel(this.model);
    return instance;
  }
}
