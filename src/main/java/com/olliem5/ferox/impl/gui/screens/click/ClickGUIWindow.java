package com.olliem5.ferox.impl.gui.screens.click;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.theme.Theme;
import com.olliem5.ferox.api.theme.ThemeManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.modules.ui.ClickGUI;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * @author olliem5
 * @author bon
 */

public final class ClickGUIWindow implements Minecraft {
	private final String name;

	public int x;
	public int y;
	private int dragX;
	private int dragY;

	private boolean dragging = false;
	public boolean open = true;

	public Category category;

	private final ArrayList<Module> modules;
	public static final ArrayList<ClickGUIWindow> windows = new ArrayList<>();

	public Theme currentTheme;

	public ClickGUIWindow(String name, int x, int y, Category category) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.category = category;
		this.modules = ModuleManager.getModulesInCategory(category);
	}

	public static void initGui() {
		int xOffset = 12;

		for (Category category : Category.values()) {
			windows.add(new ClickGUIWindow(category.toString(), xOffset, 20, category));
			xOffset += 110;
		}
	}

	public void drawGui(int mouseX, int mouseY) {
		currentTheme = ThemeManager.getThemeByName(getTheme());

		updateMousePos();
		scroll();
		collide();

		currentTheme.drawTitles(name, x, y);

		if (open) {
			currentTheme.drawModules(modules, x, y, mouseX, mouseY);
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

		for (ClickGUIWindow window : windows) {
			if (scrollWheel < 0) {
				window.setY(window.getY() - ClickGUI.scrollSpeed.getValue());

				continue;
			}

			if (scrollWheel <= 0) continue;

			window.setY(window.getY() + ClickGUI.scrollSpeed.getValue());
		}
	}

	public void collide() {
		if (!ClickGUI.windowOverflow.getValue()) {
			ScaledResolution scaledResolution = new ScaledResolution(mc);

			if (getX() <= 0) {
				setX(0);
			}

			if (getX() >= scaledResolution.getScaledWidth() - currentTheme.getWidth()) {
				setX(scaledResolution.getScaledWidth() - currentTheme.getWidth());
			}

			if (getY() <= 0) {
				setY(0);
			}

			if (getY() >= scaledResolution.getScaledHeight() - currentTheme.getHeight()) {
				setY(scaledResolution.getScaledHeight() - currentTheme.getHeight());
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