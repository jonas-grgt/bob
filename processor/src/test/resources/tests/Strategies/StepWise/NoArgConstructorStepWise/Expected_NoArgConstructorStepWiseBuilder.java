package io.jonasg.bob.test;

import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public interface NoArgConstructorStepWiseBuilder {
  static BuildStep newBuilder() {
    return new DefaultNoArgConstructorStepWiseBuilder();
  }

  interface BuildStep {
    BuildStep year(int year);

    NoArgConstructorStepWise build();
  }
}
