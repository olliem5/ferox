package me.olliem5.ferox.impl.gui.screens.click;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.theme.Theme;
import me.olliem5.ferox.api.theme.ThemeManager;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.render.gui.GuiUtil;
import me.olliem5.ferox.impl.modules.ui.ClickGUIModule;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/16/20
 */

public final class Window implements Minecraft {
	private final String name;

	public int x;
	public int y;
	private int dragX;
	private int dragY;

	private boolean dragging = false;
	private boolean open = true;

	private ArrayList<Module> modules;
	public static final ArrayList<Window> windows = new ArrayList<>();

	public Theme currentTheme;

	public Window(String name, int x, int y, Category category) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.modules = ModuleManager.getModulesInCategory(category);
	}
	
	public static void initGui() {
		int xOffset = 12;

		for (Category category : Category.values()) {
			windows.add(new Window(category.getName(), xOffset, 20, category));
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
			x = GuiUtil.mX - (dragX - x);
			y = GuiUtil.mY - (dragY - y);
		}

		dragX = GuiUtil.mX;
		dragY = GuiUtil.mY;
	}

	public void scroll() {
		int scrollWheel = Mouse.getDWheel();

		for (Window window : windows) {
			if (scrollWheel < 0) {
				window.setY(window.getY() - ClickGUIModule.scrollSpeed.getValue());

				continue;
			}

			if (scrollWheel <= 0) continue;

			window.setY(window.getY() + ClickGUIModule.scrollSpeed.getValue());
		}
	}

	public void collide() {
		if (!ClickGUIModule.windowOverflow.getValue()) {
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
		switch (ClickGUIModule.theme.getValue()) {
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
