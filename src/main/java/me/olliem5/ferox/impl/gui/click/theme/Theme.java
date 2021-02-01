package me.olliem5.ferox.impl.gui.click.theme;

import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.gui.click.theme.themes.DefaultTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bon
 * @since 11/18/20
 */

public abstract class Theme implements Minecraft {
	private String name;
	private int width;
	private int height;
	public static final List<Theme> themes = new ArrayList<>();
	
	public Theme(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public abstract void drawTitles(String name, int left, int top);

	public abstract void drawModules(List<Module> modules, int left, int top, int mouseX, int mouseY);

	public static void initThemes() {
		themes.add(new DefaultTheme());
	}
	
	public String getThemeName() { 
		return this.name; 
	}
	
	public int getThemeWidth() { 
		return this.width; 
	}
	
	public int getThemeHeight() { 
		return this.height; 
	}
	
	public static Theme getThemeByName(String name) {
		return themes.stream()
				.filter(theme -> theme.getThemeName().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
	}
}
