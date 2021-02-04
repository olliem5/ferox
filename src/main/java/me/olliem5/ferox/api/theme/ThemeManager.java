package me.olliem5.ferox.api.theme;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ThemeManager {
    private static ArrayList<Theme> themes = new ArrayList<>();

    public static void init() {
        Reflections reflections = new Reflections("me.olliem5.ferox.impl.gui.themes");

        reflections.getSubTypesOf(Theme.class).forEach(clazz -> {

            try {
                Theme theme = clazz.newInstance();
                themes.add(theme);
            } catch (InstantiationException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static ArrayList<Theme> getThemes() {
        return themes;
    }

    public static Theme getThemeByName(String name) {
        return themes.stream()
                .filter(theme -> theme.getThemeName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
