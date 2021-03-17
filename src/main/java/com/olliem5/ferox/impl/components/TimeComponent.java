package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Time", description = "Shows the current (IRL) time")
public final class TimeComponent extends Component {
    public static final Setting<HourModes> hourMode = new Setting<>("Hour", "How the hour is displayed", HourModes.Twelve);

    public TimeComponent() {
        this.addSettings(
                hourMode
        );
    }

    @Override
    public void render() {
        String renderString = "Time " + ChatFormatting.WHITE + (hourMode.getValue() == HourModes.Twelve ? new SimpleDateFormat("h:mm").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date()));
        drawString(renderString);
        this.setWidth((int) FontUtil.getStringWidth(renderString));
        this.setHeight((int) FontUtil.getStringHeight(renderString));
    }

    public enum HourModes {
        Twelve,
        TwentyFour
    }
}
