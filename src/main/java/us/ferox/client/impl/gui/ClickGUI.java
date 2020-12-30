package us.ferox.client.impl.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import us.ferox.client.api.module.Category;
import us.ferox.client.impl.gui.component.Panel;

import java.util.ArrayList;

public class ClickGUI extends GuiScreen {
    public static ArrayList<Panel> panels;

    public ClickGUI() {
        panels = new ArrayList<>();

        int panelX = 5;
        int panelY = 5;
        int panelWidth = 100;
        int panelHeight = 16;

        for (Category category : Category.values()) {
            ClickGUI.panels.add(new Panel(category.getName(), panelX, panelY, panelWidth, panelHeight, category));
            panelX += 106;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (Panel panel : panels) {
            panel.updatePosition(mouseX, mouseY);
            panel.drawScreen(mouseX, mouseY, partialTicks);

            for (Component comp : panel.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Panel panel : panels) {
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
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel panel : panels) {
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
        for (Panel panel : panels) {
            panel.setDragging(false);

            if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                for (Component component : panel.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }

    public static ArrayList<Panel> getPanels() {
        return panels;
    }

    public static Panel getPanelByName(String name) {
        Panel panel = null;

        for (Panel p : getPanels()) {
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
