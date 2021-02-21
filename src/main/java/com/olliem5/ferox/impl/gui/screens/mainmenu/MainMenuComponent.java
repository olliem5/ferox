package com.olliem5.ferox.impl.gui.screens.mainmenu;

/**
 * @author olliem5
 */

public abstract class MainMenuComponent {
    private int x;
    private int y;
    private int width;
    private int height;

    public abstract void renderComponent(int mouseX, int mouseY);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateLeftClick() {}

    public void updateRightClick() {}

    public void updateMouseState() {}
}
