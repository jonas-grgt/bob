package io.jonasg.bob;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class to be buildable, which will generate a builder for the class.
 * <p>
 * The builder will have a build method
 * that will create an instance of the annotated class.
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Buildable {
	/**
	 * List of fields to be excluded to be included in the builder.
	 *
	 * @return the list of fields to be excluded to be included in the builder.
	 */
	String[] excludeFields() default {};

	/**
	 * The setterPrefix for the generated setters.
	 * For example, "with" or "set"
	 * Defaults to no setterPrefix.
	 *
	 * @return the setterPrefix for the generated setters.
	 */
	String setterPrefix() default "";

	/**
	 * The package name of the generated builder.
	 * Defaults to the package of the annotated class.
	 *
	 * @return the package name of the generated builder
	 */
	String packageName() default "";

	/**
	 * Marks a constructor as buildable.
	 * This means that a builder will be generated
	 * using the selected constructor as opposed to the one with the most
	 * parameters.
	 */
	@SuppressWarnings("unused")
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.CONSTRUCTOR)
	@interface Constructor {
	}

}
