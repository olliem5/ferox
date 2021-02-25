package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.packet.RotationUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 */

@FeroxComponent(name = "Direction",description = "Shows the direction you are facing")
public class DirectionComponent extends Component {

    Setting<DirectionModes> directionmode = new Setting<>("Mode","Mode",DirectionModes.Normal);
    // idk why stringmode sounds kinda wack, but idk what else to put
    Setting<StringModes> stringmode = new Setting<>("StringMode","StringMode",StringModes.Full);

    public DirectionComponent() {
        this.addSettings(
                directionmode,
                stringmode
        );
    }

    @Override
    public void render() {
        String directionText;

        switch ((DirectionModes) directionmode.getValue()) {
            case Normal: {
                directionText = stringmode.getValue() == StringModes.Full ? "Direction " + ChatFormatting.WHITE + mc.player.getHorizontalFacing().getName() + ChatFormatting.RESET + RotationUtil.getFacing() : "Direction " + ChatFormatting.WHITE + ChatFormatting.RESET + RotationUtil.getFacing();
                drawString(directionText);
                this.setHeight((int) FontUtil.getStringHeight(directionText));
                this.setWidth((int) FontUtil.getStringWidth(directionText));
                break;
            }
            case Short:
                directionText = stringmode.getValue() == StringModes.Full ? "" + mc.player.getHorizontalFacing().getName() + ChatFormatting.RESET + RotationUtil.getFacing() : "" + RotationUtil.getFacing();
                drawString(directionText);
                this.setHeight((int) FontUtil.getStringHeight(directionText));
                this.setWidth((int) FontUtil.getStringWidth(directionText));
                break;
        }

    }

    public enum DirectionModes {
        Normal,
        Short
    }

    public enum StringModes {
        Full,
        Letter
    }
}
