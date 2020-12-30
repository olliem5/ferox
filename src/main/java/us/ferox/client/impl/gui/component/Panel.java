package us.ferox.client.impl.gui.component;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import us.ferox.client.Ferox;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.ClickGUI;
import us.ferox.client.impl.gui.Component;

import java.util.ArrayList;

public class Panel implements Minecraft {
    public ArrayList<Component> components;
    public String title;
    public Category category;
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

    public Panel(String title, int x, int y, int width, int height, Category category) {
        this.components = new ArrayList<>();
        this.title = title;
        this.category = category;
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

        for (Module module : Ferox.moduleManager.getModules()) {
            if (module.getCategory() == category) {
                ModuleButton moduleButton = new ModuleButton(module, this, tY);
                components.add(moduleButton);
                tY += 16;
            }
        }

        refresh();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        collide();

        Gui.drawRect(x, y, x + width, y + height, 0x75101010);

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

        for (Panel panels : ClickGUI.panels) {
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

    public Category getCategory() {
        return category;
    }
}
