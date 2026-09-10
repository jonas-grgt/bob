package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class PrivateInheritedSetterBuilder {
  private String color;

  public PrivateInheritedSetterBuilder() {
  }

  public PrivateInheritedSetterBuilder color(String color) {
    this.color = color;
    return this;
  }

  public PrivateInheritedSetter build() {
    var instance = new PrivateInheritedSetter();
    instance.setColor(this.color);
    return instance;
  }
}
