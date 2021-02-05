package me.olliem5.ferox.api.theme;

import me.olliem5.ferox.impl.gui.themes.DefaultTheme;

import java.util.ArrayList;
import java.util.Arrays;

public class ThemeManager {
    private static ArrayList<Theme> themes = new ArrayList<>();

    public static void init() {
        themes.addAll(Arrays.asList(
                new DefaultTheme()
        ));
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
