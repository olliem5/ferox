package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.EntityUtil;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

/**
 * @author olliem5
 */

public final class CSGO extends ESPMode {
    @Override
    public void drawESP(RenderWorldLastEvent event) {
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;
        float viewerYaw = mc.getRenderManager().playerViewY;

        mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> mc.player != entity)
                .filter(ESP::entityCheck)
                .forEach(entity -> {
                    RenderUtil.prepareGL();

                    Vec3d vec3d = EntityUtil.getInterpolatedPos(entity, mc.getRenderPartialTicks());

                    GL11.glLineWidth(ESP.lineWidth.getValue().floatValue());

                    GlStateManager.translate(vec3d.x - mc.getRenderManager().renderPosX, vec3d.y - mc.getRenderManager().renderPosY, vec3d.z - mc.getRenderManager().renderPosZ);
                    GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate((float) (isThirdPersonFrontal ? -1 : 1), 1.0f, 0.0f, 0.0f);

                    GL11.glColor4f(ESP.getESPColour(entity).getRed() / 255.0f, ESP.getESPColour(entity).getGreen() / 255.0f, ESP.getESPColour(entity).getBlue() / 255.0f, ESP.getESPColour(entity).getAlpha() / 255.0f);

                    GL11.glBegin(GL_LINE_LOOP); {
                        GL11.glVertex2d(-entity.width, 0);
                        GL11.glVertex2d(-entity.width, entity.height / 3);
                        GL11.glVertex2d(-entity.width, 0);
                        GL11.glVertex2d((-entity.width / 3) * 2, 0);
                    }

                    GL11.glEnd();

                    GL11.glBegin(GL_LINE_LOOP); {
                        GL11.glVertex2d(-entity.width, entity.height);
                        GL11.glVertex2d((-entity.width / 3) * 2, entity.height);
                        GL11.glVertex2d(-entity.width, entity.height);
                        GL11.glVertex2d(-entity.width, (entity.height / 3) * 2);
                    }

                    GL11.glEnd();

                    GL11.glBegin(GL_LINE_LOOP); {
                        GL11.glVertex2d(entity.width, entity.height);
                        GL11.glVertex2d((entity.width / 3) * 2, entity.height);
                        GL11.glVertex2d(entity.width, entity.height);
                        GL11.glVertex2d(entity.width, (entity.height / 3) * 2);
                    }

                    GL11.glEnd();

                    GL11.glBegin(GL_LINE_LOOP); {
                        GL11.glVertex2d(entity.width, 0);
                        GL11.glVertex2d((entity.width / 3) * 2, 0);
                        GL11.glVertex2d(entity.width, 0);
                        GL11.glVertex2d(entity.width, entity.height / 3);
                    }

                    GL11.glEnd();

                    RenderUtil.releaseGL();
                });

    }
}
