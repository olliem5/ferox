package com.feroxclient.fabric.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    EXPLOIT("Exploit"),
    MISCELLANEOUS("Miscellaneous"),
    FEROX("Ferox");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}