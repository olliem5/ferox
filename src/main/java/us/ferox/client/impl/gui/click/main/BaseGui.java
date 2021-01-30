package us.ferox.client.impl.gui.click.main;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;
import us.ferox.client.api.util.render.GuiUtil;
import us.ferox.client.impl.modules.ui.ClickGUIModule;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/16/20
 */

public class BaseGui extends GuiScreen {
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (Window window : Window.windows) {
			window.drawGui(mouseX, mouseY);
		}

		GuiUtil.updateMousePos(mouseX, mouseY);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			for (Window window : Window.windows) {
				window.updateLeftClick();
			}

			GuiUtil.updateLeftClick();
		}

		if (mouseButton == 1) {
			for (Window window : Window.windows) {
				window.updateRightClick();
			}

			GuiUtil.updateRightClick();
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);

		if (state == 0) {
			for (Window window : Window.windows) {
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
		if (ClickGUIModule.pauseGame.getValue() == ClickGUIModule.PauseGame.Pause) {
			return true;
		}

		return false;
	}
}
