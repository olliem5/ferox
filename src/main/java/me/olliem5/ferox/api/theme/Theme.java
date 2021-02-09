package me.olliem5.ferox.api.theme;

import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.traits.Minecraft;

import java.util.ArrayList;

/**
 * @author olliem5
 * @author bon
 */

public abstract class Theme implements Minecraft {
	private final String name;

	private int width;
	private int height;

	public Theme(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public abstract void drawTitles(String name, int left, int top);

	public abstract void drawModules(ArrayList<Module> modules, int left, int top, int mouseX, int mouseY);

	public abstract void drawComponents(ArrayList<Component> components, int left, int top, int mouseX, int mouseY);

	public String getName() {
		return this.name; 
	}
	
	public int getWidth() {
		return this.width; 
	}
	
	public int getHeight() {
		return this.height; 
	}
}
