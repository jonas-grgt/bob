package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class FieldHidingBuilder {
  private String make;

  public FieldHidingBuilder() {
  }

  public FieldHidingBuilder make(String make) {
    this.make = make;
    return this;
  }

  public FieldHiding build() {
    return new FieldHiding(make);
  }
}
