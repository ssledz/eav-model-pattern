package pl.softech.eav.domain.frame;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author ssledz
 * @since 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RUNTIME)
public @interface Relation {
	String name() default "";
}
