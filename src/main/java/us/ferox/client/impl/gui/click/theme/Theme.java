package us.ferox.client.impl.gui.click.theme;

import us.ferox.client.api.module.Module;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.impl.gui.click.theme.themes.DefaultTheme;

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

	public abstract void drawModules(List<Module> modules, int left, int top);

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
