package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class BooleanFieldHasOtherNameThanSetter {
    private String firstName;

    private String lastName;

    private boolean isHappy;

    public BooleanFieldHasOtherNameThanSetter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setHappy(boolean ishappy) {
        this.isHappy = ishappy;
    }
}