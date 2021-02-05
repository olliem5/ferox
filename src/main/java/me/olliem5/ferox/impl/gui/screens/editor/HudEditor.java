package me.olliem5.ferox.impl.gui.screens.editor;

import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.hud.HudManager;
import me.olliem5.ferox.impl.gui.Component;
import me.olliem5.ferox.impl.gui.screens.editor.component.HudPanel;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public final class HudEditor extends GuiScreen {
    public static ArrayList<HudPanel> panels;

    public HudEditor() {
        panels = new ArrayList<>();

        int panelX = 5;
        int panelY = 5;
        int panelWidth = 100;
        int panelHeight = 16;

        panels.add(new HudPanel("Ferox HUD", panelX, panelY, panelWidth, panelHeight));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (HudPanel panel : panels) {
            panel.updatePosition(mouseX, mouseY);
            panel.drawScreen(mouseX, mouseY, partialTicks);

            for (Component comp : panel.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }

        for (HudComponent hudComponent : HudManager.getComponents()) {
            if (hudComponent.isVisible()) {
                hudComponent.updatePosition(mouseX,mouseY);
                if (hudComponent.isDragging()) {
                    Gui.drawRect(hudComponent.getPosX() + -2, hudComponent.getPosY() + -2, hudComponent.getPosX() + hudComponent.getWidth() + 2, hudComponent.getPosY() + hudComponent.getHeight() + 2, 0x90303030);
                }
                Gui.drawRect(hudComponent.getPosX() + -1, hudComponent.getPosY() + -1, hudComponent.getPosX() + hudComponent.getWidth() + 1, hudComponent.getPosY() + hudComponent.getHeight() + 1, 0x75101010);
                hudComponent.render();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (HudPanel panel : panels) {
            if (panel.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                panel.setDragging(true);
                panel.dragX = mouseX - panel.getX();
                panel.dragY = mouseY - panel.getY();
            } else if (panel.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                panel.setOpen(!panel.isOpen());
            } else if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                for (Component component : panel.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }

        for (HudComponent hudComponent : HudManager.getComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && mouseButton == 0 && hudComponent.isVisible()) {
                hudComponent.setDragging(true);
                hudComponent.setDragX(mouseX - hudComponent.getPosX());
                hudComponent.setDragY(mouseY - hudComponent.getPosY());
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (HudPanel panel : panels) {
            if (panel.isOpen() && !panel.getComponents().isEmpty() && keyCode != Keyboard.KEY_ESCAPE) {
                for (Component component : panel.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }

        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (HudPanel panel : panels) {
            panel.setDragging(false);

            if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                for (Component component : panel.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }

        for (HudComponent hudComponent : HudManager.getComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && hudComponent.isVisible()) {
                hudComponent.setDragging(false);
            }
        }
    }

    public static ArrayList<HudPanel> getPanels() {
        return panels;
    }

    public static HudPanel getPanelByName(String name) {
        HudPanel panel = null;

        for (HudPanel p : getPanels()) {
            if (p.title.equalsIgnoreCase(name)) {
                panel = p;
            }
        }

        return panel;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
