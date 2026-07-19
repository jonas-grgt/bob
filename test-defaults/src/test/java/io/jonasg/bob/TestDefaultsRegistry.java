package io.jonasg.bob;

import java.util.HashMap;
import java.util.Map;

/**
 * Test-only stand-in for generated registry.
 */
@SuppressWarnings("unused")
public class TestDefaultsRegistry {
	private static final Map<String, String> REGISTRY = new HashMap<>();

	static {
		REGISTRY.put("io.jonasg.bob.TestDefaultsResolverTest$BuildableTestType",
				"io.jonasg.bob.TestDefaultsResolverTest$RegistryDefaultsClass");
	}

	public static String findDefaultsClassName(String buildableTypeName) {
		return REGISTRY.get(buildableTypeName);
	}
}
