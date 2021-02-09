package me.olliem5.ferox.impl.gui;

import me.olliem5.ferox.api.traits.Minecraft;

/**
 * @author olliem5
 */

public class Component implements Minecraft {
    public void renderComponent() {}

    public void updateComponent(int mouseX, int mouseY) {}

    public void mouseClicked(int mouseX, int mouseY, int button) {}

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(char typedChar, int key) {}

    public void closeAllSub() {}

    public void setOff(final int newOff) {}

    public int getHeight() {
        return 0;
    }
}