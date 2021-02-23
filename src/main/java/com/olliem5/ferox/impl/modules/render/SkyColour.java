package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;

import java.awt.*;

/**
 * @author olliem5
 * @author linustouchtips
 */

@FeroxModule(name = "SkyColour", description = "Changes the colour of the sky", category = Category.Render)
public final class SkyColour extends Module {
    public static final Setting<Boolean> sky = new Setting<>("Sky", "The colour for the sky", true);
    public static final Setting<Color> skyColour = new Setting<>(sky, "Sky Colour", "The colour for the sky", new Color(21, 191, 219, 205));

    public static final Setting<Boolean> fog = new Setting<>("Fog", "The colour for the fog", false);
    public static final Setting<Color> fogColour = new Setting<>(fog, "Fog Colour", "The colour for the fog", new Color(21, 77, 219, 205));

    public SkyColour() {
        this.addSettings(
                sky,
                fog
        );
    }

    @PaceHandler
    public void onFogColours(EntityViewRenderEvent.FogColors event) {
        if (fog.getValue()) {
            event.setRed(fogColour.getValue().getRed() / 255.0f);
            event.setGreen(fogColour.getValue().getGreen() / 255.0f);
            event.setBlue(fogColour.getValue().getBlue() / 255.0f);
        }
    }
}
