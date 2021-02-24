package com.olliem5.ferox.impl.gui.screens.mainmenu.components;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.MainMenuComponent;

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
            URL changelogPaste = new URL("https://pastebin.com/raw/ts6V1vnb");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(changelogPaste.openStream()));

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
        boost = 0;

        changelogLines.forEach(changelogLine -> {
            FontUtil.drawText(changelogLine, 2, 4 + FontUtil.getStringHeight(Ferox.NAME_VERSION) + (FontUtil.getStringHeight(changelogLine) * boost), -1);

            boost++;
        });
    }
}
