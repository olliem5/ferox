package me.olliem5.ferox.api.component;

import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.impl.modules.ui.HUDEditor;
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

    private int posX = 2;
    private int posY = 2;
    private int dragX;
    private int dragY;
    private int width;
    private int height;

    private boolean visible = false;
    private boolean dragging = false;
    private boolean opened = false;

    private final ArrayList<Setting> settings = new ArrayList<>();

    private int screenWidth = new ScaledResolution(mc).getScaledWidth();
    private int screenHeight = new ScaledResolution(mc).getScaledHeight();

    private FeroxComponent getAnnotation() {
        if (getClass().isAnnotationPresent(FeroxComponent.class)) {
            return getClass().getAnnotation(FeroxComponent.class);
        }

        throw new IllegalStateException("Annotation 'FeroxComponent' not found!");
    }

    public abstract void render();

    public void drawString(String text) {
        FontUtil.drawText(text, posX, posY, -1);
    }

    public boolean isMouseOnComponent(int x, int y) {
        return (x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height);
    }

    public void collide() {
        ScaledResolution sr = new ScaledResolution(mc);

        if (posX <= 0) {
            setPosX(0);
        }

        if (posX >= sr.getScaledWidth() - width) {
            setPosX(sr.getScaledWidth() - width);
        }

        if (posY <= 0) {
            setPosY(0);
        }

        if (getPosY() >= sr.getScaledHeight() - height) {
            setPosY(sr.getScaledHeight() - height);
        }
    }

    public boolean isTopLeft() {
        return (this.getPosX() < (screenWidth / 2) && this.getPosY() < (screenHeight) / 2);
    }

    public boolean isBottomLeft() {
        return (this.getPosX() < (screenWidth / 2) && this.getPosY() > (screenHeight) / 2);
    }

    public boolean isTopRight() {
        return (this.getPosX() > (screenWidth / 2) && this.getPosY() < (screenHeight) / 2);
    }

    public boolean isBottomRight() {
        return (this.getPosX() > (screenWidth / 2) && this.getPosY() > (screenHeight) / 2);
    }

    public ScreenPosition getScreenPosition() {
        if (this.isTopLeft()) {
            return ScreenPosition.TopLeft;
        } else if (this.isBottomLeft()) {
            return ScreenPosition.BottomLeft;
        } else if (this.isTopRight()) {
            return ScreenPosition.TopRight;
        } else {
         return ScreenPosition.BottomRight;
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.setPosX(mouseX - getDragX());
            this.setPosY(mouseY - getDragY());
        }

        if (!HUDEditor.componentOverflow.getValue()) {
            collide();
        }
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public Setting addSetting(Setting setting) {
        settings.add(setting);

        return setting;
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

    public final int getPosX() {
        return posX;
    }

    public final void setPosX(int posX) {
        this.posX = posX;
    }

    public final int getPosY() {
        return posY;
    }

    public final void setPosY(int posY) {
        this.posY = posY;
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

    public enum ScreenPosition {
        TopLeft,
        BottomLeft,
        TopRight,
        BottomRight
    }
}
