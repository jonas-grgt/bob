package io.jonasg.bob;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides default values for fields by reflecting over a dedicated defaults
 * class.
 *
 * <p>
 * The defaults class is expected to expose {@code public static} fields whose
 * names
 * correspond to the requested keys. Values are retrieved once and cached for
 * efficient lookup.
 * </p>
 *
 * <p>
 * If the defaults class or a specific field cannot be resolved, a type-based
 * fallback
 * value is returned (e.g. {@code 0} for {@code int}, {@code null} for
 * {@code String}).
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 */
public final class DefaultsProvider {

	private static final Map<Class<?>, Object> DEFAULTS = Map.ofEntries(
			Map.entry(int.class, 0),
			Map.entry(long.class, 0L),
			Map.entry(boolean.class, false),
			Map.entry(byte.class, (byte) 0),
			Map.entry(short.class, (short) 0),
			Map.entry(char.class, '\0'),
			Map.entry(float.class, 0f),
			Map.entry(double.class, 0d));

	private final Class<?> defaultsClass;

	private final Map<String, Field> fields;

	private DefaultsProvider(Class<?> defaultsClass) {
		this.defaultsClass = defaultsClass;
		this.fields = initFields(defaultsClass);
	}

	/**
	 * Retrieves the default value for the given field name and type.
	 *
	 * <p>
	 * If a matching {@code public static} field exists in the defaults class,
	 * its value is returned. Otherwise, a fallback value based on the requested
	 * type
	 * is returned.
	 * </p>
	 *
	 * @param name
	 *            the field name
	 * @param type
	 *            the expected type of the value
	 * @param <T>
	 *            the type of the value
	 * @return the resolved default value, or a fallback if not found
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name, Class<T> type) {
		if (defaultsClass != null) {
			Field field = fields.get(name);
			if (field != null) {
				try {
					return (T) field.get(null);
				} catch (IllegalAccessException | NullPointerException ignored) {
				}
			}
		}
		return defaultValue(type);
	}

	/**
	 * Creates a {@link DefaultsProvider} for the given fully qualified class name.
	 *
	 * <p>
	 * If the class cannot be found, the provider will still be created but will
	 * always return fallback default values.
	 * </p>
	 *
	 * @param className
	 *            fully qualified name of the defaults class
	 * @return a {@link DefaultsProvider} instance (never {@code null})
	 */
	public static DefaultsProvider of(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return new DefaultsProvider(clazz);
		} catch (ClassNotFoundException e) {
			return new DefaultsProvider(null);
		}
	}

	private Map<String, Field> initFields(Class<?> clazz) {
		if (clazz == null) {
			return Map.of();
		}

		return Arrays.stream(clazz.getFields())
				.collect(Collectors.toUnmodifiableMap(Field::getName, Function.identity()));
	}

	@SuppressWarnings("unchecked")
	private <T> T defaultValue(Class<T> type) {
		return (T) DEFAULTS.getOrDefault(type, null);
	}
}
