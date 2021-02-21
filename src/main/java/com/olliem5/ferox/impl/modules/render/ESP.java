package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import com.olliem5.ferox.impl.modules.render.esp.modes.CSGO;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "ESP", description = "Highlights entities in your world", category = Category.Render)
public final class ESP extends Module {
    public static final Setting<ESPModes> mode = new Setting<>("Mode", "The mode to use for ESP", ESPModes.CSGO);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>("Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows chams to function on crystals", true);
    public static final Setting<Color> crystalColour = new Setting<>(crystals, "Crystal Colour", "The colour for crystals", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> players = new Setting<>("Players", "Allows chams to function on players", true);
    public static final Setting<Color> playerColour = new Setting<>(players, "Player Colour", "The colour for players", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> animals = new Setting<>("Animals", "Allows chams to function on animals", true);
    public static final Setting<Color> animalColour = new Setting<>(animals, "Animal Colour", "The colour for animals", new Color(75, 219, 22, 100));

    public static final Setting<Boolean> mobs = new Setting<>("Mobs", "Allows chams to function on animals", true);
    public static final Setting<Color> mobColour = new Setting<>(mobs, "Mob Colour", "The colour for animals", new Color(236, 13, 29, 100));

    public ESP() {
        this.addSettings(
                mode,
                outlineWidth,
                crystals,
                players,
                animals,
                mobs
        );
    }

    private ESPMode espMode;

    public void onUpdate() {
        switch (mode.getValue()) {
            case CSGO:
                espMode = new CSGO();
                break;
        }
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        espMode.drawESP(event);
    }

    public enum ESPModes {
        CSGO
    }
}
