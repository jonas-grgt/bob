package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public record GenericRecord<T extends Comparable<T>>(T value) {
}
