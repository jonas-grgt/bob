package io.jonasg.bob;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a constructor as buildable.
 * This means that a builder will be generated
 * using the selected constructor as opposed to the one with the most
 * parameters.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
public @interface BuildableConstructor {
}
