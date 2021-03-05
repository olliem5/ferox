package com.olliem5.ferox.api.theme;

import com.olliem5.ferox.impl.gui.themes.DefaultTheme;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author olliem5
 */

public final class ThemeManager {
    private static final ArrayList<Theme> themes = new ArrayList<>();

    public static void init() {
        themes.addAll(Collections.singletonList(
                new DefaultTheme()
        ));
    }

    public static Theme getThemeByName(String name) {
        return themes.stream()
                .filter(theme -> theme.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
