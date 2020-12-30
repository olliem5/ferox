package us.ferox.client.api.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    MISC("Miscellaneous"),
    RENDER("Render"),
    FEROX("Ferox");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}