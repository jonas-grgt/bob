package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class SingleCharacterFieldName {
    private int x;

    public SingleCharacterFieldName(int x) {
        this.x = x;
    }

    public void move(int dx) {
    }
}
