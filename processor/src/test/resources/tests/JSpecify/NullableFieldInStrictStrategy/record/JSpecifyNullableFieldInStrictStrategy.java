package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;
import org.jspecify.annotations.Nullable;

@Buildable(strategy = Strategy.STRICT)
public record JSpecifyNullableFieldInStrictStrategy(String make, @Nullable String model) {
}
