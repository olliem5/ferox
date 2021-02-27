package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.util.math.MathHelper;

/**
 * @author olliem5
 */

@FeroxComponent(name = "TPS", description = "Shows the current TPS")
public final class TPSComponent extends Component {
    private float[] ticks = new float[20];

    @Override
    public void render() {
        String renderString = "TPS " + ChatFormatting.WHITE + String.format("%.2f", getTickRate());
        drawString(renderString);
        this.setWidth((int) FontUtil.getStringWidth(renderString));
        this.setHeight((int) FontUtil.getStringHeight(renderString));
    }

    private float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;

        for (int i = 0; i < this.ticks.length; i++) {
            float tick = this.ticks[i];

            if (tick > 0.0f) {
                tickRate += tick;
                tickCount++;
            }
        }

        return MathHelper.clamp((tickRate / tickCount), 0.0f, 20.0f);
    }
}
