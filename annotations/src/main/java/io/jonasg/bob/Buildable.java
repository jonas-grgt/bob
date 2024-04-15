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
	 * Specifies the constructor policy to be applied when building an instance of
	 * the annotated class.
	 * The policy controls how strictly the builder enforces the setting of fields
	 * during object instantiation.
	 *
	 * <p>
	 * The default value is {@link ConstructorPolicy#PERMISSIVE}, which allows for
	 * more flexibility in field initialization,
	 * permitting fields to be optionally set with defaults provided for unset
	 * fields. Alternatively,
	 * setting this to {@link ConstructorPolicy#ENFORCED} requires all fields to be
	 * explicitly set, otherwise,
	 * the builder throws an exception.
	 * </p>
	 *
	 * @return the constructor policy used by the builder
	 */
	ConstructorPolicy constructorPolicy() default ConstructorPolicy.PERMISSIVE;

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
