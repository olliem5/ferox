package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.Objects;

/**
 * @author olliem5
 */

public final class Glow extends ESPMode {
    @Override
    public void drawESP(RenderWorldLastEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> mc.player != entity)
                .filter(ESP::entityCheck)
                .forEach(entity -> entity.setGlowing(true));
    }
}
