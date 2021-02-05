package me.olliem5.ferox.impl.gui.screens.editor.component;

import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.hud.HudManager;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.impl.gui.Component;
import me.olliem5.ferox.impl.gui.screens.editor.HudEditor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public final class HudPanel implements Minecraft {
    public ArrayList<Component> components;
    public String title;
    public int x;
    public int y;
    public int dragX;
    public int dragY;
    public int width;
    public int height;
    public int tY;
    public boolean isSettingOpen;
    private boolean isDragging;
    private boolean open;

    public HudPanel(String title, int x, int y, int width, int height) {
        this.components = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.dragX = 0;
        this.dragY = 0;
        this.width = width;
        this.height = height;
        this.tY = this.height;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;

        for (HudComponent hudComponent : HudManager.getComponents()) {
            HudComponentButton hudComponentButton = new HudComponentButton(hudComponent, this, tY);
            components.add(hudComponentButton);
            tY += 16;
        }

        refresh();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        collide();

        Gui.drawRect(x + 1, y + 1, x + width -1, y + height -1, RainbowUtil.getRainbow().getRGB());
        Gui.drawRect(x, y, x + width, y + height, new Color(20, 20, 20, 150).getRGB());

        FontUtil.drawText(title, x + 2 + width / 2 - FontUtil.getStringWidth(title) / 2, y + height / 2 - FontUtil.getStringHeight(title) / 2, -1);

        if (open && !components.isEmpty()) {
            for (Component component : components) {
                component.renderComponent();
            }
        }
    }

    public void refresh() {
        int off = height;

        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            setX(mouseX - dragX);
            setY(mouseY - dragY);
        }

        scroll();
    }

    public void collide() {
        ScaledResolution sr = new ScaledResolution(mc);

        if (getX() <= 0) {
            setX(0);
        }

        if (getX() >= sr.getScaledWidth() - width) {
            setX(sr.getScaledWidth() - width);
        }

        if (getY() <= 0){
            setY(0);
        }

        if (getY() >= sr.getScaledHeight() - height) {
            setY(sr.getScaledHeight() - height);
        }
    }

    public void scroll() {
        int scrollWheel = Mouse.getDWheel();

        for (HudPanel panels : HudEditor.panels) {
            if (scrollWheel < 0) {
                panels.setY(panels.getY() - 10);
                continue;
            }

            if (scrollWheel <= 0) continue;

            panels.setY(panels.getY() + 10);
        }
    }

    public void closeAllSetting() {
        for (Component component : components) {
            component.closeAllSub();
        }
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDragging(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
}
