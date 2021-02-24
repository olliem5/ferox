package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 */


@FeroxComponent(name = "Gamemode", description = "Shows the gamemode you are in on screen")
public class GamemodeComponent extends Component {

    //lol gamemodemode
    Setting<GamemodeModes> gamemodemode = new Setting("Mode","Mode",GamemodeModes.Normal);

    public GamemodeComponent() {
        addSettings(
                gamemodemode
        );
    }

    @Override
    public void render() {
        String gamemode;
        switch ((GamemodeModes) gamemodemode.getValue()) {
            case Normal: {
                                                                //annoyingly this will always have a lowercase First letter :(
                gamemode = "Gamemode " + ChatFormatting.WHITE + mc.playerController.getCurrentGameType().getName();
                drawString(gamemode);
                this.setHeight((int) FontUtil.getStringHeight(gamemode));
                this.setWidth((int) FontUtil.getStringWidth(gamemode));
                break;
            }
            case OnlyGamemode: {
                gamemode = "" + ChatFormatting.WHITE + mc.playerController.getCurrentGameType().getName();
                drawString(gamemode);
                this.setHeight((int) FontUtil.getStringHeight(gamemode));
                this.setWidth((int) FontUtil.getStringWidth(gamemode));
                break;
            }
        }
    }

    public enum GamemodeModes {
        Normal, OnlyGamemode

    }
}
