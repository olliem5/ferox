package com.olliem5.ferox.api.theme;

import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.traits.Minecraft;

import java.util.ArrayList;

/**
 * @author olliem5
 * @author bon
 */

public abstract class Theme implements Minecraft {
	private final String name = getAnnotation().name();

	private final int width = getAnnotation().width();
	private final int height = getAnnotation().height();

	private FeroxTheme getAnnotation() {
		if (getClass().isAnnotationPresent(FeroxTheme.class)) {
			return getClass().getAnnotation(FeroxTheme.class);
		}

		throw new IllegalStateException("Annotation 'FeroxTheme' not found!");
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
