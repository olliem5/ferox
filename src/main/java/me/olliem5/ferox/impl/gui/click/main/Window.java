package me.olliem5.ferox.impl.gui.click.main;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.render.GuiUtil;
import me.olliem5.ferox.impl.gui.click.theme.Theme;
import me.olliem5.ferox.impl.modules.ui.ClickGUIModule;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import me.olliem5.ferox.Ferox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/16/20
 */

public class Window implements Minecraft {
	public int x;
	public int y;
	private int lastMouseX;
	private int lastMouseY;
	private String name;
	private boolean dragging;
	private boolean open = true;
	private List<Module> modules;
	public static final List<Window> windows = new ArrayList<>();
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
		currentTheme = Theme.getThemeByName(getTheme());

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
			x = GuiUtil.mX - (lastMouseX - x);
			y = GuiUtil.mY - (lastMouseY - y);
		}

		lastMouseX = GuiUtil.mX;
		lastMouseY = GuiUtil.mY;
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

			if (getX() >= sr.getScaledWidth() - currentTheme.getThemeWidth()) {
				setX(sr.getScaledWidth() - currentTheme.getThemeWidth());
			}

			if (getY() <= 0) {
				setY(0);
			}

			if (getY() >= sr.getScaledHeight() - currentTheme.getThemeHeight()) {
				setY(sr.getScaledHeight() - currentTheme.getThemeHeight());
			}
		}
	}

	public void updateLeftClick() {
		if (GuiUtil.mouseOver(x, y, x + currentTheme.getThemeWidth(), y + currentTheme.getThemeHeight())) {
			dragging = true;
		}
	}

	public void updateRightClick() {
		if (GuiUtil.mouseOver(x, y, x + currentTheme.getThemeWidth(), y + currentTheme.getThemeHeight())) {
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
