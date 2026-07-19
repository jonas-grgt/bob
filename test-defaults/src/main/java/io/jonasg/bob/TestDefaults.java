package io.jonasg.bob;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a container for test-scoped default values
 * that are applied at runtime via reflection.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TestDefaults {
	/**
	 * The buildable type this test defaults class provides values for.
	 * Defaults to {@link Buildable.DefaultsAsInnerClass}, which means the
	 * annotated class must be a static inner class of the buildable type.
	 * Set this explicitly when the test defaults class is a top-level class,
	 * e.g. {@code @TestDefaults(Car.class)}.
	 *
	 * @return the buildable type these test defaults apply to
	 */
	Class<?> value() default Buildable.DefaultsAsInnerClass.class;
}
