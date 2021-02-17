package com.olliem5.ferox.impl.gui.screens.editor;

import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.ComponentManager;
import com.olliem5.ferox.api.theme.Theme;
import com.olliem5.ferox.api.theme.ThemeManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.modules.ui.HUDEditor;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * @author olliem5
 * @author bon
 */

public final class HUDEditorWindow implements Minecraft {
	private final String name;

	public int x;
	public int y;
	private int dragX;
	private int dragY;

	private boolean dragging = false;
	private boolean open = true;

	private final ArrayList<Component> components;
	public static final ArrayList<HUDEditorWindow> windows = new ArrayList<>();

	public Theme currentTheme;

	public HUDEditorWindow(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.components = ComponentManager.getComponents();
	}
	
	public static void initGui() {
		windows.add(new HUDEditorWindow("Ferox HUD", 12, 20));
	}
	
	public void drawGui(int mouseX, int mouseY) {
		currentTheme = ThemeManager.getThemeByName(getTheme());

		updateMousePos();
		scroll();
		collide();

		currentTheme.drawTitles(name, x, y);

		if (open) {
			currentTheme.drawComponents(components, x, y, mouseX, mouseY);
		}
	}
	
	private void updateMousePos() {
		if (dragging) {
			x = GuiUtil.mouseX - (dragX - x);
			y = GuiUtil.mouseY - (dragY - y);
		}

		dragX = GuiUtil.mouseX;
		dragY = GuiUtil.mouseY;
	}

	public void scroll() {
		int scrollWheel = Mouse.getDWheel();

		for (HUDEditorWindow window : windows) {
			if (scrollWheel < 0) {
				window.setY(window.getY() - HUDEditor.scrollSpeed.getValue());

				continue;
			}

			if (scrollWheel <= 0) continue;

			window.setY(window.getY() + HUDEditor.scrollSpeed.getValue());
		}
	}

	public void collide() {
		if (!HUDEditor.windowOverflow.getValue()) {
			ScaledResolution sr = new ScaledResolution(mc);

			if (getX() <= 0) {
				setX(0);
			}

			if (getX() >= sr.getScaledWidth() - currentTheme.getWidth()) {
				setX(sr.getScaledWidth() - currentTheme.getWidth());
			}

			if (getY() <= 0) {
				setY(0);
			}

			if (getY() >= sr.getScaledHeight() - currentTheme.getHeight()) {
				setY(sr.getScaledHeight() - currentTheme.getHeight());
			}
		}
	}

	public void updateLeftClick() {
		if (GuiUtil.mouseOver(x, y, x + currentTheme.getWidth(), y + currentTheme.getHeight())) {
			dragging = true;
		}
	}

	public void updateRightClick() {
		if (GuiUtil.mouseOver(x, y, x + currentTheme.getWidth(), y + currentTheme.getHeight())) {
			open = !open;
		}
	}

	private String getTheme() {
		switch (HUDEditor.theme.getValue()) {
			case Default:
				return "Default";
		}

		return "Default";
	}

	public void updateMouseState() {
		dragging = false;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
