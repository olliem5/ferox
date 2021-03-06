package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

/**
 * @author olliem5
 */

public final class Hitbox extends ESPMode {
    @Override
    public void drawESP(RenderWorldLastEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> mc.player != entity)
                .filter(ESP::entityCheck)
                .forEach(entity -> {
                    RenderUtil.prepareGL();

                    GL11.glLineWidth(ESP.lineWidth.getValue().floatValue());

                    RenderGlobal.drawSelectionBoundingBox(entity.getRenderBoundingBox(), ESP.getESPColour(entity).getRed() / 255.0f, ESP.getESPColour(entity).getGreen() / 255.0f, ESP.getESPColour(entity).getBlue() / 255.0f, ESP.getESPColour(entity).getAlpha() / 255.0f);

                    RenderUtil.releaseGL();
                });
    }
}
