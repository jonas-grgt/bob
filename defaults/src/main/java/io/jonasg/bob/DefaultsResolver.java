package io.jonasg.bob;

import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Applies runtime defaults to builders.
 *
 * <p>
 * The resolver first tries to apply defaults from {@code @Buildable.Defaults}
 * via generated code and then delegates to {@code TestDefaultsResolver} when
 * available on the classpath.
 * </p>
 */
public final class DefaultsResolver {

	private static final Logger logger = Logger.getLogger(DefaultsResolver.class.getName());
	private static final Set<String> fineLogOnce = ConcurrentHashMap.newKeySet();

	private DefaultsResolver() {
	}

	public static void applyDefaults(Object builder, Class<?> buildableType) {
		applyDefaults(builder, buildableType, null);
	}

	public static void applyDefaults(Object builder, Class<?> buildableType, @Nullable String setterPrefix) {
		logFineOnce(
				"lookup:" + buildableType.getName(),
				"Checking runtime defaults for " + buildableType.getName());
		try {
			Class<?> resolverClass = Class.forName("io.jonasg.bob.TestDefaultsResolver");
			if (setterPrefix == null || setterPrefix.isEmpty()) {
				Method method = resolverClass.getMethod("applyDefaults", Object.class, Class.class);
				method.invoke(null, builder, buildableType);
			} else {
				Method method = resolverClass.getMethod("applyDefaults", Object.class, Class.class, String.class);
				method.invoke(null, builder, buildableType, setterPrefix);
			}
			logFineOnce(
					"delegated:" + buildableType.getName(),
					"Delegated runtime defaults resolution for " + buildableType.getName()
							+ " to TestDefaultsResolver");
		} catch (ClassNotFoundException e) {
			logFineOnce(
					"missing:test-resolver",
					"TestDefaultsResolver not found on classpath (bob-test-defaults missing); skipping test defaults resolution");
		} catch (ReflectiveOperationException e) {
			logger.log(Level.FINE, "Failed to apply test defaults reflectively", e);
		}
	}

	private static void logFineOnce(String key, String message) {
		if (fineLogOnce.add(key)) {
			logger.log(Level.FINE, message);
		}
	}
}
