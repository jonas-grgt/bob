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
	 * List of fields that should be set within the building process.
	 * If not, an {@link MandatoryFieldMissingException} will be thrown.
	 *
	 * @return mandatory fields
	 */
	String[] mandatoryFields() default {};

	/**
	 * Specifies the strategy to be applied when generating a builder of
	 * the annotated class.
	 * The strategy mostly controls how strictly the builder enforces the setting of
	 * fields
	 * during object instantiation.
	 *
	 * <p>
	 * The default value is {@link Strategy#PERMISSIVE}, which allows for
	 * more flexibility in field initialization,
	 * permitting fields to be optionally set with defaults provided for unset
	 * fields. Alternatively,
	 * setting this to {@link Strategy#STRICT} requires all mandatory fields
	 * to be
	 * explicitly set, otherwise,
	 * the builder throws an {@link MandatoryFieldMissingException}. <br/>
	 * Setting it to {@link Strategy#ALLOW_NULLS} requires all
	 * fields
	 * to be explicitly set, otherwise the builder throws an exception. But by using
	 * this policy, null can also be set.
	 * </p>
	 *
	 * @return the constructor policy used by the builder
	 */
	Strategy[] strategy() default Strategy.PERMISSIVE;

	/**
	 * Marks a constructor as buildable.
	 * This means that a builder will be generated
	 * using the selected constructor as opposed to the one with the most
	 * parameters.
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.CONSTRUCTOR)
	@interface Constructor {
	}

	/**
	 * Marks a field as mandatory. When the field is not set within the building
	 * process
	 * an {@link MandatoryFieldMissingException} will be thrown.
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.FIELD)
	@interface Mandatory {
	}

}
