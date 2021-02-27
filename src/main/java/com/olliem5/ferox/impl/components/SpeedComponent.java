package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.util.math.MathHelper;

/**
 * @author olliem5
 *
 * TODO: km/h
 */

@FeroxComponent(name = "Speed", description = "Shows how fast you are going")
public final class SpeedComponent extends Component {
    @Override
    public void render() {
        double x = mc.player.posX - mc.player.prevPosX;
        double z = mc.player.posZ - mc.player.prevPosZ;

        float tickRate = (mc.timer.tickLength / 1000.0f);

        String renderString = "Speed " + ChatFormatting.WHITE + String.format("%.1f", (double) (MathHelper.sqrt(x * x + z * z) / tickRate)) + "bps";
        drawString(renderString);
        this.setWidth((int) FontUtil.getStringWidth(renderString));
        this.setHeight((int) FontUtil.getStringHeight(renderString));
    }
}
