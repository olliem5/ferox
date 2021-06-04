package com.feroxclient.fabric.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleManifest {
    String name();
    String description() default "No description provided!";
    Category category();
    int key() default -1;
}