package com.olliem5.ferox.impl.gui.screens.mainmenu.components;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.gui.screens.mainmenu.MainMenuComponent;
import com.olliem5.ferox.impl.modules.ferox.MainMenu;

/**
 * @author olliem5
 */

public final class LogoComponent extends MainMenuComponent {
    @Override
    public void renderComponent(int mouseX, int mouseY) {
        if (MainMenu.logo.getValue()) {
            FontUtil.drawText(Ferox.NAME_VERSION, 2, 2, -1);
        }
    }
}
