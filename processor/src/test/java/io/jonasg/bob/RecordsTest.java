package io.jonasg.bob;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RecordsTest {

	@Test
	public void recordsAreBuildable() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/RecordsAreBuildable/RecordsAreBuildable.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.RecordsAreBuildableBuilder")
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource(
								"/tests/RecordsAreBuildable/Expected_RecordsAreBuildable.java"))
				.executeTest();
	}

}
