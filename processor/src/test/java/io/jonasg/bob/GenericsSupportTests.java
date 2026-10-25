package io.jonasg.bob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.toolisticon.cute.Cute;

/**
 * Generated builders for generic types must compile and be usable with concrete
 * type arguments (issue #112). Each fixture ships a usage class that compiles
 * against the generated builder and returns a summary of the built instance.
 */
class GenericsSupportTests {

	@ParameterizedTest(name = "{0}")
	@CsvSource(delimiter = '|', value = {
			"RecursiveBound     | RecursiveBound.java   | RecursiveBoundUsage   | compare=1",
			"NonJavaLangBound   | NonJavaLangBound.java | NonJavaLangBoundUsage | volvo/2020/1",
			"GenericRecord      | GenericRecord.java    | GenericRecordUsage    | abc/-1",
			"GenericNestedType  | Shelf.java            | ShelfUsage            | book/fiction",
			"GenericNestedTypeOtherPackage | Cabinet.java | CabinetUsage | socks",
			"StepWiseGeneric    | StepWiseGeneric.java  | StepWiseGenericUsage  | a/0/n",
	})
	void generatedBuilderCompilesAndIsUsableWithTypeArguments(String scenario, String buildable, String usage,
			String expected) {
		String dir = "/tests/GenericsSupport/" + scenario + "/";
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(dir + buildable, dir + usage + ".java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedClassesTestedSuccessfullyBy(classLoader -> {
					Class<?> usageClass = classLoader.getClass("io.jonasg.bob.test." + usage);
					assertEquals(expected, usageClass.getMethod("run").invoke(null));
				})
				.executeTest();
	}
}
