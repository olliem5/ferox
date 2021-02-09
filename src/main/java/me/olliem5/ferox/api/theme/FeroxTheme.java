package me.olliem5.ferox.api.theme;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author olliem5
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface FeroxTheme {
    String name();
    int width();
    int height();
}
