package me.olliem5.ferox.api.hud;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FeroxComponent {
    String name();
    String description() default "No description provided!";
}
