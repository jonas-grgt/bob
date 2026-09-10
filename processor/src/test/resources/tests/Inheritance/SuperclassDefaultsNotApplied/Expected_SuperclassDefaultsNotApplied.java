package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class SuperclassDefaultsNotAppliedBuilder {
  private String color;

  private String make;

  public SuperclassDefaultsNotAppliedBuilder() {
  }

  public SuperclassDefaultsNotAppliedBuilder color(String color) {
    this.color = color;
    return this;
  }

  public SuperclassDefaultsNotAppliedBuilder make(String make) {
    this.make = make;
    return this;
  }

  public SuperclassDefaultsNotApplied build() {
    var instance = new SuperclassDefaultsNotApplied();
    instance.setColor(this.color);
    instance.setMake(this.make);
    return instance;
  }
}
