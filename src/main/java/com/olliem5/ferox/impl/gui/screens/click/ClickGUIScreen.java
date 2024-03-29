package com.olliem5.ferox.impl.gui.screens.click;

import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.modules.ui.ClickGUI;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;

import java.io.IOException;

/**
 * @author olliem5
 * @author bon
 */

public final class ClickGUIScreen extends GuiScreen {
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (ClickGUI.backgroundMode.getValue() == ClickGUI.BackgroundModes.Vanilla) {
			this.drawDefaultBackground();
		}

		for (ClickGUIWindow window : ClickGUIWindow.windows) {
			window.drawGui(mouseX, mouseY);
		}

		GuiUtil.updateMousePos(mouseX, mouseY);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			for (ClickGUIWindow window : ClickGUIWindow.windows) {
				window.updateLeftClick();
			}

			GuiUtil.updateLeftClick();
		}

		if (mouseButton == 1) {
			for (ClickGUIWindow window : ClickGUIWindow.windows) {
				window.updateRightClick();
			}

			GuiUtil.updateRightClick();
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);

		if (state == 0) {
			for (ClickGUIWindow window : ClickGUIWindow.windows) {
				window.updateMouseState();
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
		if (OpenGlHelper.shadersSupported) {
			try {
				mc.entityRenderer.getShaderGroup().deleteShaderGroup();
			} catch (Exception ignored) {}
		}

		ConfigUtil.saveConfig();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return ClickGUI.pauseGame.getValue() == ClickGUI.PauseModes.Pause;
	}
}
