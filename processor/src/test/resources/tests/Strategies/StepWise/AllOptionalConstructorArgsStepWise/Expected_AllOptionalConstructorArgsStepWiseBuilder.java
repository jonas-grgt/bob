package io.jonasg.bob.test;

import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public interface AllOptionalConstructorArgsStepWiseBuilder {
  static BuildStep newBuilder() {
    return new DefaultAllOptionalConstructorArgsStepWiseBuilder();
  }

  interface BuildStep {
    BuildStep year(int year);

    AllOptionalConstructorArgsStepWise build();
  }
}
