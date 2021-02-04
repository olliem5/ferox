package me.olliem5.ferox.api.theme;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GUITheme {
    String name();
}
