package io.jonasg.bob;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.toolisticon.cute.Cute;
import io.toolisticon.cute.GeneratedFileObjectMatcher;

/**
 * Constructors and setters the generated builder cannot access must not be used
 * (issue #111). Compilation of the generated builder fails otherwise.
 */
class AccessibilityTests {

	private static GeneratedFileObjectMatcher contains(String text) {
		return file -> file.getCharContent(true).toString().contains(text);
	}

	private static GeneratedFileObjectMatcher doesNotContain(String text) {
		return file -> !file.getCharContent(true).toString().contains(text);
	}

	@Test
	void privateConstructorIsNotSelected() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/Accessibility/PrivateConstructorIsIgnored/PrivateConstructorIsIgnored.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PrivateConstructorIsIgnoredBuilder")
				.matches(contains("new PrivateConstructorIsIgnored(make)"))
				.executeTest();
	}

	@Test
	void privateSetterOnClassIsNotUsed() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles("/tests/Accessibility/PrivateSetterIsIgnored/PrivateSetterIsIgnored.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PrivateSetterIsIgnoredBuilder")
				.matches(doesNotContain(".setMake("))
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PrivateSetterIsIgnoredBuilder")
				.matches(contains(".setYear(this.year)"))
				.executeTest();
	}

	@Test
	void packagePrivateMembersAreNotUsedWhenBuilderIsInOtherPackage() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Accessibility/PackagePrivateIgnoredInOtherPackage/PackagePrivateIgnoredInOtherPackage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.other.PackagePrivateIgnoredInOtherPackageBuilder")
				.matches(contains("new PackagePrivateIgnoredInOtherPackage(make)"))
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.other.PackagePrivateIgnoredInOtherPackageBuilder")
				.matches(doesNotContain(".setYear("))
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.other.PackagePrivateIgnoredInOtherPackageBuilder")
				.matches(contains(".setEngineSize(this.engineSize)"))
				.executeTest();
	}

	@Test
	void packagePrivateMembersAreUsedWhenBuilderIsInSamePackage() {
		Cute.blackBoxTest()
				.given()
				.processors(List.of(BuildableProcessor.class))
				.andSourceFiles(
						"/tests/Accessibility/PackagePrivateUsedInSamePackage/PackagePrivateUsedInSamePackage.java")
				.whenCompiled()
				.thenExpectThat()
				.compilationSucceeds()
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PackagePrivateUsedInSamePackageBuilder")
				.matches(contains("new PackagePrivateUsedInSamePackage(make, year)"))
				.andThat()
				.generatedSourceFile("io.jonasg.bob.test.PackagePrivateUsedInSamePackageBuilder")
				.matches(contains(".setEngineSize(this.engineSize)"))
				.executeTest();
	}
}
