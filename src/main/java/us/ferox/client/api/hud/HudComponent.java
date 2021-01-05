package us.ferox.client.api.hud;

import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.api.util.font.FontUtil;

public abstract class HudComponent implements Minecraft {
    private final String name;
    private int posX, posY;
    protected boolean visible = false;

    protected HudComponent(String name, int posX, int posY) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void render();

    protected final void drawString(String text) {
        FontUtil.drawString(text, posX, posY, HudManager.getColor());
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
}
