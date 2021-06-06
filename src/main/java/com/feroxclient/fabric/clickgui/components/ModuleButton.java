package com.feroxclient.fabric.clickgui.components;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.clickgui.Component;
import com.feroxclient.fabric.clickgui.Frame;
import com.feroxclient.fabric.module.Module;
import com.feroxclient.fabric.util.render.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

public class ModuleButton extends Component {

    private ArrayList<Component> subcomponents;
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean open;
    private boolean hovered;

    public ModuleButton(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<>();
        this.open = false;
        int opY = offset + 15;

    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 15;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 15;
        }
    }

    @Override
    public void renderComponent(MatrixStack matrixStack) {
        RenderUtil.drawRect(parent.x, parent.y + offset, parent.width, parent.height,  mod.isEnabled() ? new Color(59, 26, 203) :new Color(51, 39, 101) );
        RenderUtil.drawLine(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset, new Color(255, 0, 101));
        mc.textRenderer.draw(matrixStack, mod.getName(), parent.x + (parent.width /2) - (mc.textRenderer.getWidth(mod.getName()) /2), parent.y + offset + + (parent.height /2) - (mc.textRenderer.fontHeight /2), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }

        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (!this.isOpen()) {
                this.setOpen(true);
            } else {
                this.setOpen(false);
            }
        }

        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(key);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.getX() && x < parent.getX() + 100 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 15 + this.offset) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
