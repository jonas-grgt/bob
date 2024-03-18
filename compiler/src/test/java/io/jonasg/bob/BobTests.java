package io.jonasg.bob;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.JavaFileObjectUtils;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class BobTests {

	private static final Logger LOG = LoggerFactory.getLogger(BobTests.class);

	@Test
	void runAllTests() {
		var testsFolder = new File("src/test/resources/tests");
		Arrays.stream(testsFolder.listFiles(File::isDirectory))
				.forEach(this::runTest);
	}

	private void runTest(File testFolder) {
		LOG.info(() -> "Running " + testFolder.getName() + " test");
		Cute.blackBoxTest()
				.given()
				.processors(BuildableProcessor.class)
				.andSourceFiles("/tests/%s/%1$s.java".formatted(testFolder.getName()))
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.builder.%sBuilder".formatted(testFolder.getName()))
				.matches(
						CuteApi.ExpectedFileObjectMatcherKind.BINARY,
						JavaFileObjectUtils.readFromResource("/tests/%s/Expected_%1$s.java".formatted(testFolder.getName())))
				.executeTest();
	}
}
