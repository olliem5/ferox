package com.olliem5.ferox.impl.gui.screens.mainmenu;

import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.render.draw.DrawUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.components.ChangelogComponent;
import com.olliem5.ferox.impl.gui.screens.mainmenu.components.LogoComponent;
import com.olliem5.ferox.impl.modules.ferox.MainMenu;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

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

        if (MainMenu.mouseBackground.getValue()) {
            DrawUtil.drawCompleteImage(-16.0f + xOffset, -16.0f + yOffset, width + 32.0f, height + 32.0f);
        } else {
            DrawUtil.drawCompleteImage(-16.0f, -16.0f, width + 16.0f, height + 16.0f);
        }

        drawGUIButton("Mods", width / 2 - 62, height / 2);
        drawGUIButton("Singleplayer", width / 2 - 124, height / 2);
        drawGUIButton("Multiplayer", width / 2, height / 2);
        drawGUIButton("Options", width / 2 + 62, height / 2);
        drawGUIButton("Quit", width / 2 + 124, height / 2);

        for (MainMenuComponent mainMenuComponent : mainMenuComponents) {
            mainMenuComponent.renderComponent(mouseX, mouseY);
        }

        GuiUtil.updateMousePos(mouseX, mouseY);
    }

    private void drawGUIButton(String text, int x, int y) {
        boolean mouseOver = false;

        if (GuiUtil.mouseOver(x - 30, y - 15, x + 30, y + 15)) {
            mouseOver = true;
        }

        if (!mouseOver) {
            Gui.drawRect(x - 30, y - 15, x + 30, y + 15, new Color(20, 20, 20, 125).getRGB());
        } else {
            Gui.drawRect(x - 30, y - 15, x + 30, y + 15, new Color(40, 40, 40, 125).getRGB());
        }

        FontUtil.drawText(text, x - (FontUtil.getStringWidth(text) / 2), y - (FontUtil.getStringHeight(text) / 2), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            if (GuiUtil.mouseOver((width / 2 - 62) - 30, (height / 2) - 15, (width / 2 - 62) + 30, (height / 2) + 15)) {
                mc.displayGuiScreen(new GuiModList(this));
            }

            if (GuiUtil.mouseOver((width / 2 - 124) - 30, (height / 2) - 15, (width / 2 - 124) + 30, (height / 2) + 15)) {
                mc.displayGuiScreen(new GuiWorldSelection(this));
            }

            if (GuiUtil.mouseOver((width / 2) - 30, (height / 2) - 15, (width / 2) + 30, (height / 2) + 15)) {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (GuiUtil.mouseOver((width / 2 + 62) - 30, (height / 2) - 15, (width / 2 + 62) + 30, (height / 2) + 15)) {
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            }

            if (GuiUtil.mouseOver((width / 2 + 124) - 30, (height / 2) - 15, (width / 2 + 124) + 30, (height / 2) + 15)) {
                mc.shutdown();
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
