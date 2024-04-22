package io.jonasg.bob;

/**
 * Enumeration that outlines the strategies by which builders are generated.
 * <p>
 * For the most part, these strategies define how mandatory fields are handled
 * within the
 * built processing, ranging from strict enforcement of field values to more
 * lenient approaches allowing defaults.
 * </p>
 * <p>
 * Mandatory fields are those annotated with {@link Buildable.Mandatory} and
 * those fields
 * set by the selected constructor.
 * </p>
 */
public enum Strategy {
	/**
	 * This policy permits the construction of an object even if not all
	 * constructor parameters are set or set to null.
	 * Fields not explicitly set will automatically initialize to their default
	 * values
	 * (e.g., {@code null} for object references, {@code 0} for integers,
	 * and {@code false} for booleans).
	 * It is suitable for scenarios where not all fields require explicit values,
	 * allowing for more flexible object creation.
	 * <p>
	 * This strategy is not combinable with the usage of {@link Buildable.Mandatory}
	 * and will throw an Exception in that case.
	 * </p>
	 */
	PERMISSIVE,

	/**
	 * Requires that all mandatory fields to be explicitly set.
	 * If a field is not set or set to null, the builder will throw an
	 * {@link MandatoryFieldMissingException}, ensuring
	 * that the constructed object is fully initialized with all specified fields,
	 * which promotes immutability and thread-safety.
	 */
	STRICT,

	/**
	 * This policy generates a step builder pattern to enforce a structured
	 * sequence
	 * of all constructor fields.
	 * Guiding the user to set constructor fields in a predefined order,
	 * where each step must be completed before proceeding to the next.
	 * Ultimately, ending up with all fields set
	 * before being able to build the object or add other none constructor fields.
	 */
	STEP_WISE,

	/**
	 * Accompanying strategy that allows mandatory fields to be explicitly set to
	 * {@code null}.
	 * This strategy is combinable with {@link #STRICT} and {@link #STEP_WISE} only.
	 * <br/>
	 * If a field is omitted, the builder will throw an
	 * {@link MandatoryFieldMissingException}, maintaining strict initialization
	 * while offering the flexibility of allowing null values.
	 */
	ALLOW_NULLS
}
