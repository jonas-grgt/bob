package io.jonasg.bob;

/**
 * Enumerates the policies for constructor behavior in the {@link Buildable}
 * annotation context.
 * This enumeration defines how the object construction must handle its
 * constructor fields during
 * instantiation through the builder pattern.
 * <p>
 * These policies allow developers to specify whether all fields must be
 * explicitly set
 * before constructing an object or if missing fields can be initialized with
 * default values.
 * </p>
 */
public enum ConstructorPolicy {
	/**
	 * Requires all fields to be explicitly set in the constructor.
	 * If any field is not set, the builder will throw an exception.
	 * This policy ensures that the constructed object is fully initialized
	 * with all the specified fields, promoting immutability and thread-safety,
	 * assuming that all fields are properly handled.
	 */
	ENFORCED,

	ENFORCED_STEPWISE,

	/**
	 * Requires all fields
	 * to be explicitly set with a concrete value or {@code null} in the
	 * constructor.
	 * If any field is not set, the builder will throw an exception.
	 * The main difference with {@link ConstructorPolicy#ENFORCED} is that fields
	 * are considered
	 * to be set to, even if set explicitly to {@code null}
	 */
	ENFORCED_ALLOW_NULLS,

	/**
	 * Allows the object to be constructed even if not all constructor parameters
	 * have
	 * been explicitly set. Fields not explicitly set will be initialized to their
	 * default values
	 * (e.g., {@code null} for object references, {@code 0} for {@code int},
	 * {@code false} for {@code boolean}, etc.).
	 * <p>
	 * This policy is suitable for situations where not all fields are required
	 * to have a value, allowing for more flexible object creation.
	 * </p>
	 */
	PERMISSIVE
}
