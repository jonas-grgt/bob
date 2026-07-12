package io.jonasg.bob;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unused")
class TestDefaultsResolverTest {

	public static class SimpleBuilder {
		private String name;
		private int age;
		private boolean active;

		public void name(String name) {
			this.name = name;
		}

		public void age(int age) {
			this.age = age;
		}

		public void active(boolean active) {
			this.active = active;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public boolean isActive() {
			return active;
		}
	}

	public static class InnerDefaultsOwner {
		@Buildable.TestDefaults
		public static class Defaults {
			public static String name = "InnerDefault";
			public static int age = 99;
		}
	}

	@Test
	void appliesDefaultsFromInnerClassAnnotatedWithTestDefaults() {
		var builder = new SimpleBuilder();
		TestDefaultsResolver.applyDefaults(builder, InnerDefaultsOwner.class);

		assertThat(builder.name).isEqualTo("InnerDefault");
		assertThat(builder.age).isEqualTo(99);
	}

	public static class TopLevelDefaultsOwner {
		@Buildable.TestDefaults(TopLevelDefaultsOwner.class)
		public static class MyDefaults {
			public static String name = "TopLevelDefault";
		}
	}

	@Test
	void appliesDefaultsFromTopLevelClassWithExplicitValue() {
		var builder = new SimpleBuilder();
		TestDefaultsResolver.applyDefaults(builder, TopLevelDefaultsOwner.class);

		assertThat(builder.name).isEqualTo("TopLevelDefault");
	}

	public static class SupplierBuilder {
		private String uniqueName;

		public void uniqueName(String uniqueName) {
			this.uniqueName = uniqueName;
		}

		public String getUniqueName() {
			return uniqueName;
		}
	}

	public static class SupplierDefaultsOwner {
		@Buildable.TestDefaults
		public static class Defaults {
			public static Supplier<String> uniqueName = () -> "supplier-value-" + UUID.randomUUID();
		}
	}

	@Test
	void unwrapsSupplierDefaults() {
		var builder = new SupplierBuilder();
		TestDefaultsResolver.applyDefaults(builder, SupplierDefaultsOwner.class);

		assertThat(builder.uniqueName).startsWith("supplier-value");
	}

	public static class PrefixedBuilder {
		private String name;
		private int age;

		public void withName(String name) {
			this.name = name;
		}

		public void withAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}
	}

	public static class PrefixedDefaultsOwner {
		@Buildable.TestDefaults
		public static class Defaults {
			public static String name = "PrefixedDefault";
			public static int age = 42;
		}
	}

	@Test
	void appliesDefaultsWithSetterPrefix() {
		var builder = new PrefixedBuilder();
		TestDefaultsResolver.applyDefaults(builder, PrefixedDefaultsOwner.class, "with");

		assertThat(builder.name).isEqualTo("PrefixedDefault");
		assertThat(builder.age).isEqualTo(42);
	}

	public static class NoDefaultsBuilder {
		private String name;

		public void name(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static class NoDefaultsClass {
	}

	@Test
	void doesNothingWhenNoDefaultsClassExists() {
		var builder = new NoDefaultsBuilder();
		TestDefaultsResolver.applyDefaults(builder, NoDefaultsClass.class);

		assertThat(builder.name).isNull();
	}

	public static class NoMatchingSetterBuilder {
		private Double engineSize;

		public void engineSize(Double engineSize) {
			this.engineSize = engineSize;
		}

		public Double getEngineSize() {
			return engineSize;
		}
	}

	@Test
	void doesNothingWhenBuilderHasNoMatchingSetters() {
		var builder = new NoMatchingSetterBuilder();
		TestDefaultsResolver.applyDefaults(builder, InnerDefaultsOwner.class);

		assertThat(builder.engineSize).isNull();
	}
}
