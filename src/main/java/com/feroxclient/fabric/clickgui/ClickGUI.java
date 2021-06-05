package com.feroxclient.fabric.clickgui;

import com.feroxclient.fabric.util.render.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

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
        RenderUtil.prepare(matrices);
        //render util is usable in here
        RenderUtil.end(matrices);
    }
}
