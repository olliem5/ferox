package com.olliem5.ferox.impl.gui.screens.mainmenu;

import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.components.LogoComponent;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class FeroxGuiMainMenu extends GuiScreen {
    public static final ArrayList<MainMenuComponent> mainMenuComponents = new ArrayList<>();

    public void initGui() {
        mainMenuComponents.add(new LogoComponent());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
            mainMenuComponent.renderComponent(mouseX, mouseY);
        }

        GuiUtil.updateMousePos(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
                mainMenuComponent.updateLeftClick();
            }

            GuiUtil.updateLeftClick();
        }

        if (mouseButton == 1) {
            for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
                mainMenuComponent.updateRightClick();
            }

            GuiUtil.updateRightClick();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (state == 0) {
            for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
                mainMenuComponent.updateMouseState();
            }

            GuiUtil.updateMouseState();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        GuiUtil.updateKeyState(keyCode);
    }

    @Override
    public void onGuiClosed() {
        ConfigUtil.saveConfig();
    }
}
