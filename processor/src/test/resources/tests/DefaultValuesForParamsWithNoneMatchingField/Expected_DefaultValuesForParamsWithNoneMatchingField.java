package io.jonasg.bob.test;

import io.jonasg.bob.TestDefaultsResolver;

public final class DefaultValuesForParamsWithNoneMatchingFieldBuilder {
  public DefaultValuesForParamsWithNoneMatchingFieldBuilder() {
    TestDefaultsResolver.applyDefaults(this, DefaultValuesForParamsWithNoneMatchingField.class);
  }

  public DefaultValuesForParamsWithNoneMatchingField build() {
    return new DefaultValuesForParamsWithNoneMatchingField(null, 0, null, 0.0d, null, false, null, 0.0f, null, 0L, null, null);
  }
}
