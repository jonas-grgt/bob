package io.jonasg.bob.test;

public class AllOptionalConstructorArgsStepWiseUsage {
	AllOptionalConstructorArgsStepWise withoutOptionals() {
		AllOptionalConstructorArgsStepWiseBuilder.BuildStep step = AllOptionalConstructorArgsStepWiseBuilder.newBuilder();
		return step.build();
	}

	AllOptionalConstructorArgsStepWise withOptionals() {
		return AllOptionalConstructorArgsStepWiseBuilder.newBuilder()
				.year(2024)
				.build();
	}
}
