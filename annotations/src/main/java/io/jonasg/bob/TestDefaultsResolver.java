package io.jonasg.bob;

import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resolves and applies default values to a builder at runtime via reflection.
 * This enables test source set {@link Buildable.TestDefaults} classes that are
 * not
 * visible to the annotation processor at compile time.
 */
public final class TestDefaultsResolver {

	private static final Logger logger = Logger.getLogger(TestDefaultsResolver.class.getName());

	private TestDefaultsResolver() {
	}

	/**
	 * Applies - using the builders setters - default values from a
	 * {@link Buildable.TestDefaults} class to the
	 * given builder instance.
	 *
	 * @param builder
	 *            the builder instance to apply defaults to
	 * @param buildableType
	 *            the type annotated with {@link Buildable}
	 */
	public static void applyDefaults(
			Object builder,
			Class<?> buildableType) {
		applyDefaults(builder, buildableType, null);
	}

	/**
	 * Applies - using the builders prefixed setters - default values from a
	 * {@link Buildable.TestDefaults} class to the
	 * given builder instance.
	 *
	 * @param builder
	 *            the builder instance to apply defaults to
	 * @param buildableType
	 *            the type annotated with {@link Buildable}
	 * @param setterPrefix
	 *            the setter prefix used by the builder (empty string
	 *            if none)
	 */
	public static void applyDefaults(
			Object builder,
			Class<?> buildableType,
			@Nullable String setterPrefix) {
		Class<?> defaultsClass = findDefaultsClass(buildableType);
		if (defaultsClass == null) {
			return;
		}

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
		// Check for inner class annotated with @Buildable.TestDefaults
		for (Class<?> inner : buildableType.getDeclaredClasses()) {
			if (inner.isAnnotationPresent(Buildable.TestDefaults.class)) {
				return inner;
			}
		}

		// Check for standalone Builders: {TypeName}Defaults
		try {
			Class<?> defaultsClass = Class.forName(buildableType.getName() + "Defaults");
			if (defaultsClass.isAnnotationPresent(Buildable.TestDefaults.class)) {
				return defaultsClass;
			}
		} catch (ClassNotFoundException e) {
			// not found
		}

		return null;
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
		if (primitive == int.class) {
			return Integer.class;
		}
		if (primitive == long.class) {
			return Long.class;
		}
		if (primitive == float.class) {
			return Float.class;
		}
		if (primitive == double.class) {
			return Double.class;
		}
		if (primitive == boolean.class) {
			return Boolean.class;
		}
		if (primitive == byte.class) {
			return Byte.class;
		}
		if (primitive == short.class) {
			return Short.class;
		}
		if (primitive == char.class) {
			return Character.class;
		}
		return primitive;
	}
}
