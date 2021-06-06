package com.feroxclient.fabric.clickgui;

import com.feroxclient.fabric.util.MinecraftTrait;
import net.minecraft.client.util.math.MatrixStack;

public class Component implements MinecraftTrait {

    public void renderComponent(MatrixStack matrixStack) {}

    public void updateComponent(int mouseX, int mouseY) {}

    public void mouseClicked(int mouseX, int mouseY, int button) {}

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(int key) {}

    public void setOff(final int newOff) {}

    public int getHeight() {
        return 0;
    }
}