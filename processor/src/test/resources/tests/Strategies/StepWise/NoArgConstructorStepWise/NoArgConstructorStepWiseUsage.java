package io.jonasg.bob.test;

public class NoArgConstructorStepWiseUsage {
	NoArgConstructorStepWise withoutOptionals() {
		NoArgConstructorStepWiseBuilder.BuildStep step = NoArgConstructorStepWiseBuilder.newBuilder();
		return step.build();
	}

	NoArgConstructorStepWise withOptionals() {
		return NoArgConstructorStepWiseBuilder.newBuilder()
				.year(2024)
				.build();
	}
}
