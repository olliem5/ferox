package com.olliem5.ferox.api.module;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author olliem5
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface FeroxModule {
    String name();
    String description() default "No description provided!";
    Category category();
    int key() default Keyboard.KEY_NONE;
}