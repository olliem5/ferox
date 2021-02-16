package com.olliem5.ferox.api.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author olliem5
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface FeroxComponent {
    String name();
    String description() default "No description provided!";
}
