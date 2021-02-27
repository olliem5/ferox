package com.olliem5.ferox.impl.gui.screens.mainmenu.components;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.MainMenuComponent;
import com.olliem5.ferox.impl.modules.ferox.MainMenu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class ChangelogComponent extends MainMenuComponent {
    public ArrayList<String> changelogLines = new ArrayList<>();

    public ChangelogComponent() {
        try {
            URL changelog = new URL("https://raw.githubusercontent.com/olliem5/ferox-resources/master/changelog.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(changelog.openStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                changelogLines.add(line);
            }
        } catch (Exception exception) {
            Ferox.log("Changelog reading from the URL failed! Do you an internet connection?");
        }
    }

    private int boost;

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        if (MainMenu.changelog.getValue()) {
            boost = 0;

            changelogLines.forEach(changelogLine -> {
                FontUtil.drawText(changelogLine, 2, (4 + FontUtil.getStringHeight(Ferox.NAME_VERSION)) + (10 * boost), -1);

                boost++;
            });
        }
    }
}
