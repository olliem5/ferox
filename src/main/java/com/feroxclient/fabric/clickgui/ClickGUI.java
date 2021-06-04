package com.feroxclient.fabric.clickgui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGUI extends Screen{
    public ClickGUI() {
        super(Text.of("ClickGUI"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }
}
