package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Buildable.Mandatory;
import io.jonasg.bob.Strategy;

@Buildable(strategy = Strategy.STRICT)
public record MandatoryAnnotationWithDefaults(@Mandatory String make) {
}
