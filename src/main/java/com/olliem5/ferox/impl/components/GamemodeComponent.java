package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "Gamemode", description = "Shows the gamemode you are in on screen")
public final class GamemodeComponent extends Component {
    public static final Setting<GamemodeModes> gamemodeMode = new Setting<>("Mode", "The way of displaying the gamemode", GamemodeModes.Normal);

    public GamemodeComponent() {
        this.addSettings(
                gamemodeMode
        );
    }

    @Override
    public void render() {
        String renderString;
        String gamemodeString = mc.playerController.getCurrentGameType().getName();
        String gamemodeOutput = gamemodeString.substring(0, 1).toUpperCase();

        switch (gamemodeMode.getValue()) {
            case Normal:
                renderString = "Gamemode " + ChatFormatting.WHITE + gamemodeOutput;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "" + ChatFormatting.WHITE + gamemodeOutput;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    public enum GamemodeModes {
        Normal,
        Short
    }
}
