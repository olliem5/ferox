package com.feroxclient.fabric.clickgui;

import com.feroxclient.fabric.module.Category;
import com.feroxclient.fabric.util.render.RenderUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;


public class ClickGUI extends Screen{
    public ClickGUI() {
        super(Text.of("ClickGUI"));
        int panelX = 5;
        int panelY = 5;
        int panelWidth = 100;
        int panelHeight = 15;

        for (Category c : Category.values()) {
            frames.add(new Frame(c, panelX, panelY, panelWidth, panelHeight));
            panelX += 105;
        }
    }

    public static ArrayList<Frame> frames = new ArrayList<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtil.prepare(matrices);
        for(Frame frame:frames){
            frame.updatePosition(mouseX, mouseY);
            frame.render(matrices, mouseX, mouseY);
        }
        RenderUtil.end(matrices);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        for(Frame frame:frames) {
            frame.keyTyped(keyCode);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(Frame frame:frames) {
            frame.mouseReleased((int) mouseX, (int) mouseY, button);
        }
        return false;
    }
}
