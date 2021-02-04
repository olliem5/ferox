package me.olliem5.ferox.api.theme;

import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.traits.Minecraft;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/18/20
 */

public abstract class Theme implements Minecraft {
	private String name = getAnnotation().name();

	private int width;
	private int height;

	private GUITheme getAnnotation() {
		if (getClass().isAnnotationPresent(GUITheme.class)) {
			return getClass().getAnnotation(GUITheme.class);
		}

		throw new IllegalStateException("Annotation 'GUITheme' not found!");
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
