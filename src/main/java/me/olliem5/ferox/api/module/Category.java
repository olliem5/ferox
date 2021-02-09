package me.olliem5.ferox.api.module;

/**
 * @author olliem5
 */

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    MISC("Miscellaneous"),
    EXPLOIT("Exploit"),
    RENDER("Render"),
    FEROX("Ferox"),
    UI("Interface");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}