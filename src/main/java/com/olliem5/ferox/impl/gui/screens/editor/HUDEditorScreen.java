package com.olliem5.ferox.impl.gui.screens.editor;

import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.ComponentManager;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.modules.ui.HUDEditor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * @author olliem5
 * @author bon
 */

public final class HUDEditorScreen extends GuiScreen {
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (HUDEditorWindow window : HUDEditorWindow.windows) {
			window.drawGui(mouseX, mouseY);
		}

		for (Component component : ComponentManager.getComponents()) {
			if (component.isVisible()) {
				component.updatePosition(mouseX, mouseY);

				Gui.drawRect(component.getPosX(), component.getPosY(), component.getPosX() + component.getWidth(), component.getPosY() + component.getHeight(), component.isDragging() ? 0x75101010 : 0x90303030);

				component.render();
			}
		}

		GuiUtil.updateMousePos(mouseX, mouseY);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			for (HUDEditorWindow window : HUDEditorWindow.windows) {
				window.updateLeftClick();
			}

			for (Component component : ComponentManager.getComponents()) {
				if (component.isMouseOnComponent(mouseX, mouseY) && component.isVisible()) {
					component.setDragging(true);

					component.setDragX(mouseX - component.getPosX());
					component.setDragY(mouseY - component.getPosY());
				}
			}

			GuiUtil.updateLeftClick();
		}

		if (mouseButton == 1) {
			for (HUDEditorWindow window : HUDEditorWindow.windows) {
				window.updateRightClick();
			}

			GuiUtil.updateRightClick();
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);

		if (state == 0) {
			for (HUDEditorWindow window : HUDEditorWindow.windows) {
				window.updateMouseState();
			}

			for (Component component : ComponentManager.getComponents()) {
				if (component.isMouseOnComponent(mouseX, mouseY) && component.isVisible()) {
					component.setDragging(false);
				}
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
		ModuleManager.getModuleByName("HUDEditor").setEnabled(false);
		ConfigUtil.saveConfig();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		if (HUDEditor.pauseGame.getValue() == HUDEditor.PauseModes.Pause) {
			return true;
		}

		return false;
	}
}
