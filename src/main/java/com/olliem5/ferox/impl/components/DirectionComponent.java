package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.packet.RotationUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "Direction", description = "Shows the direction you are facing")
public class DirectionComponent extends Component {
    public static final Setting<DirectionModes> directionMode = new Setting<>("Mode","Mode", DirectionModes.Normal);
    public static final Setting<StringModes> stringMode = new Setting<>("String","StringMode", StringModes.Full);

    public DirectionComponent() {
        this.addSettings(
                directionMode,
                stringMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (directionMode.getValue()) {
            case Normal:
                renderString = stringMode.getValue() == StringModes.Full ? "Direction " + ChatFormatting.WHITE + mc.player.getHorizontalFacing().getName() + ChatFormatting.RESET + RotationUtil.getFacing() : "Direction " + ChatFormatting.WHITE + ChatFormatting.RESET + RotationUtil.getFacing();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = stringMode.getValue() == StringModes.Full ? "" + mc.player.getHorizontalFacing().getName() + ChatFormatting.RESET + RotationUtil.getFacing() : "" + RotationUtil.getFacing();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
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
