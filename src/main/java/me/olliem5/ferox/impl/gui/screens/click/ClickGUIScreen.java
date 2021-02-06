package me.olliem5.ferox.impl.gui.screens.click;

import me.olliem5.ferox.api.util.render.gui.GuiUtil;
import me.olliem5.ferox.impl.modules.ui.ClickGUI;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/16/20
 */

public final class ClickGUIScreen extends GuiScreen {
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

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

	/**
	 * TODO: Save config here
	 */

	@Override
	public void onGuiClosed() {

	}
	
	@Override
	public boolean doesGuiPauseGame() {
		if (ClickGUI.pauseGame.getValue() == ClickGUI.PauseModes.Pause) {
			return true;
		}

		return false;
	}
}
