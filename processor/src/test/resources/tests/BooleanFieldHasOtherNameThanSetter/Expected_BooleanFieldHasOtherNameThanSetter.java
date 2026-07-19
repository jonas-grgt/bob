package io.jonasg.bob.test;

import java.lang.String;

public final class BooleanFieldHasOtherNameThanSetterBuilder {
  private String firstName;

  private String lastName;

  private boolean isHappy;

  public BooleanFieldHasOtherNameThanSetterBuilder() {
  }

  public BooleanFieldHasOtherNameThanSetterBuilder firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public BooleanFieldHasOtherNameThanSetterBuilder lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public BooleanFieldHasOtherNameThanSetterBuilder isHappy(boolean isHappy) {
    this.isHappy = isHappy;
    return this;
  }

  public BooleanFieldHasOtherNameThanSetter build() {
    var instance = new BooleanFieldHasOtherNameThanSetter(firstName, lastName);
    instance.setHappy(this.isHappy);
    return instance;
  }
}
