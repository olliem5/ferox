package com.olliem5.ferox.impl.gui.screens.mainmenu;

import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.render.draw.DrawUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.components.ChangelogComponent;
import com.olliem5.ferox.impl.gui.screens.mainmenu.components.LogoComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author olliem5
 */

public final class FeroxGuiMainMenu extends GuiScreen {
    private static final ArrayList<MainMenuComponent> mainMenuComponents = new ArrayList<>();

    public void initGui() {
        mainMenuComponents.addAll(Arrays.asList(
                new LogoComponent(),
                new ChangelogComponent()
        ));
    }

    private final ResourceLocation backgroundImage = new ResourceLocation("ferox", "images/main_menu.jpg");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        float xOffset = -1.0f * ((mouseX - width / 2.0f) / (width / 32.0f));
        float yOffset = -1.0f * ((mouseY - height / 2.0f) / (height / 32.0f));

        mc.getTextureManager().bindTexture(backgroundImage);
        DrawUtil.drawCompleteImage(-16.0f + xOffset, -16.0f + yOffset, width + 32.0f, height + 32.0f);

        drawGUIButton("Mods", (int) (width / 2 - (FontUtil.getStringWidth("Singleplayer") * 4) - 4), height / 2);
        drawGUIButton("Singleplayer", (int) (width / 2 - (FontUtil.getStringWidth("Multiplayer") * 2) - 4), height / 2);
        drawGUIButton("Multiplayer", width / 2, height / 2);
        drawGUIButton("Options", (int) (width / 2 + (FontUtil.getStringWidth("Multiplayer") * 2) + 4), height / 2);
        drawGUIButton("Quit", (int) (width / 2 + (FontUtil.getStringWidth("Options") * 4) + 4), height / 2);

        for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
            mainMenuComponent.renderComponent(mouseX, mouseY);
        }

        GuiUtil.updateMousePos(mouseX, mouseY);
    }

    private void drawGUIButton(String text, int x, int y) {
        boolean mouseOver = false;

        if (GuiUtil.mouseOver(x - (int) (FontUtil.getStringWidth(text)), (int) (y - FontUtil.getStringHeight(text)), (x + (int) (FontUtil.getStringWidth(text))), (int) (y + (FontUtil.getStringHeight(text) * 2)))) {
            mouseOver = true;
        }

        if (!mouseOver) {
            Gui.drawRect(x - (int) (FontUtil.getStringWidth(text)), (int) (y - FontUtil.getStringHeight(text)), (x + (int) (FontUtil.getStringWidth(text))), (int) (y + (FontUtil.getStringHeight(text) * 2)), new Color(20, 20, 20, 125).getRGB());
        } else {
            Gui.drawRect(x - (int) (FontUtil.getStringWidth(text)), (int) (y - FontUtil.getStringHeight(text)), (x + (int) (FontUtil.getStringWidth(text))), (int) (y + (FontUtil.getStringHeight(text) * 2)), new Color(40, 40, 40, 125).getRGB());
        }

        FontUtil.drawText(text, x - FontUtil.getStringWidth(text) / 2, y, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            if (GuiUtil.mouseOver((width / 2) - (int) (FontUtil.getStringWidth("Multiplayer")), (int) ((height / 2) - FontUtil.getStringHeight("Multiplayer")), ((width / 2) + (int) (FontUtil.getStringWidth("Multiplayer"))), (int) ((height / 2) + (FontUtil.getStringHeight("Multiplayer") * 2)))) {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }

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
