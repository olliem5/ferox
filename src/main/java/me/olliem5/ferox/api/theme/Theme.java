package me.olliem5.ferox.api.theme;

import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.traits.Minecraft;

import java.util.ArrayList;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/18/20
 */

public abstract class Theme implements Minecraft {
	private String name;

	private int width;
	private int height;

	public Theme(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public abstract void drawTitles(String name, int left, int top);

	public abstract void drawModules(ArrayList<Module> modules, int left, int top, int mouseX, int mouseY);

	public String getThemeName() { 
		return this.name; 
	}
	
	public int getThemeWidth() { 
		return this.width; 
	}
	
	public int getThemeHeight() { 
		return this.height; 
	}
}
