package com.olliem5.ferox.api.component;

import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;
import com.olliem5.ferox.impl.modules.ui.HUDEditor;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author olliem5
 * @author Reap
 */

public abstract class Component implements Minecraft {
    private final String name = getAnnotation().name();
    private final String description = getAnnotation().description();

    private int x = 2;
    private int y = 2;
    private int dragX;
    private int dragY;
    private int width;
    private int height;

    private boolean visible = false;
    private boolean dragging = false;
    private boolean opened = false;

    private final ArrayList<Setting> settings = new ArrayList<>();

    private FeroxComponent getAnnotation() {
        if (getClass().isAnnotationPresent(FeroxComponent.class)) {
            return getClass().getAnnotation(FeroxComponent.class);
        }

        throw new IllegalStateException("Annotation 'FeroxComponent' not found!");
    }

    public abstract void render();

    public void drawString(String text) {
        FontUtil.drawText(text, x, y, Colours.clientColourPicker.getValue().getRGB());
    }

    public boolean isMouseOnComponent(int x, int y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }

    public void collide() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        if (getX() <= 0) {
            setX(0);
        }

        if (getX() >= scaledResolution.getScaledWidth() - width) {
            setX(scaledResolution.getScaledWidth() - width);
        }

        if (getY() <= 0) {
            setY(0);
        }

        if (getY() >= scaledResolution.getScaledHeight() - height) {
            setY(scaledResolution.getScaledHeight() - height);
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.setX(mouseX - getDragX());
            this.setY(mouseY - getDragY());
        }

        if (!HUDEditor.componentOverflow.getValue()) {
            collide();
        }
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public final String getName() {
        return name;
    }

    public final int getX() {
        return x;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final int getY() {
        return y;
    }

    public final void setY(int y) {
        this.y = y;
    }

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getWidth() {
        return width;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public String getDescription() {
        return description;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
