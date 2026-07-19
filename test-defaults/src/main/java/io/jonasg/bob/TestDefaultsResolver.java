package io.jonasg.bob;

import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resolves and applies test defaults to a builder at runtime via reflection.
 */
public final class TestDefaultsResolver {

	private static final Logger logger = Logger.getLogger(TestDefaultsResolver.class.getName());
	private static final String TEST_DEFAULTS_ANNOTATION = "io.jonasg.bob.TestDefaults";
	private static final Set<String> fineLogOnce = ConcurrentHashMap.newKeySet();

	private TestDefaultsResolver() {
	}

	public static void applyDefaults(
			Object builder,
			Class<?> buildableType) {
		applyDefaults(builder, buildableType, null);
	}

	public static void applyDefaults(
			Object builder,
			Class<?> buildableType,
			@Nullable String setterPrefix) {
		logFineOnce(
				"lookup:" + buildableType.getName(),
				"Checking test defaults for " + buildableType.getName());
		Class<?> defaultsClass = findDefaultsClass(buildableType);
		if (defaultsClass == null) {
			logFineOnce(
					"not-found:" + buildableType.getName(),
					"No test defaults found for " + buildableType.getName());
			return;
		}
		logFineOnce(
				"resolved:" + buildableType.getName(),
				"Resolved test defaults class " + defaultsClass.getName() + " for " + buildableType.getName());

		Arrays.stream(defaultsClass.getDeclaredFields()).forEach(field -> {
			if (!Modifier.isStatic(field.getModifiers())) {
				logger.log(Level.WARNING, "Defaults class " + defaultsClass.getName() + " with ignored field "
						+ field.getName() + " because non-static.");
				return;
			}
			try {
				Object value = field.get(null);
				if (value == null) {
					logger.log(Level.WARNING, "Defaults class " + defaultsClass.getName() + " with ignored field "
							+ field.getName() + " due to null value");
					return;
				}

				if (value instanceof Supplier<?> supplier) {
					value = supplier.get();
				}

				String setterName = setterName(field.getName(), setterPrefix);
				Method setter = findSetter(builder.getClass(), setterName, value.getClass());
				if (setter != null) {
					setter.invoke(builder, value);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"Failed to apply default value for " + field.getName() + " from " + defaultsClass.getName() +
								"due to: " + e);
			}
		});
	}

	private static @Nullable Class<?> findDefaultsClass(Class<?> buildableType) {
		for (Class<?> inner : buildableType.getDeclaredClasses()) {
			if (hasTestDefaultsAnnotation(inner)) {
				return inner;
			}
		}

		try {
			Class<?> registryClass = Class.forName("io.jonasg.bob.TestDefaultsRegistry");
			Method findMethod = registryClass.getMethod("findDefaultsClassName", String.class);
			String defaultsClassName = (String) findMethod.invoke(null, buildableType.getName());
			if (defaultsClassName != null) {
				return Class.forName(defaultsClassName);
			}
		} catch (Exception e) {
			// registry not available or lookup failed
		}

		return null;
	}

	private static boolean hasTestDefaultsAnnotation(Class<?> type) {
		return Arrays.stream(type.getAnnotations())
				.anyMatch(annotation -> annotation.annotationType().getName().equals(TEST_DEFAULTS_ANNOTATION));
	}

	private static String setterName(String fieldName, @Nullable String prefix) {
		if (prefix == null || prefix.isEmpty()) {
			return fieldName;
		}
		return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	private static @Nullable Method findSetter(Class<?> clazz, String name, Class<?> valueType) {
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(name) && method.getParameterCount() == 1) {
				Class<?> paramType = method.getParameterTypes()[0];
				if (isAssignable(paramType, valueType)) {
					return method;
				}
			}
		}
		return null;
	}

	private static boolean isAssignable(Class<?> paramType, Class<?> valueType) {
		if (paramType.isAssignableFrom(valueType)) {
			return true;
		}
		if (paramType.isPrimitive()) {
			return boxType(paramType).isAssignableFrom(valueType);
		}
		if (valueType.isPrimitive()) {
			return paramType.isAssignableFrom(boxType(valueType));
		}
		return false;
	}

	private static Class<?> boxType(Class<?> primitive) {
		return MethodType.methodType(primitive).wrap().returnType();
	}

	private static void logFineOnce(String key, String message) {
		if (fineLogOnce.add(key)) {
			logger.log(Level.FINE, message);
		}
	}
}
