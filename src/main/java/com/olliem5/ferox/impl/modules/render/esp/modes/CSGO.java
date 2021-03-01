package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.EntityUtil;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author olliem5
 */

public final class CSGO extends ESPMode {
    @Override
    public void drawESP(RenderWorldLastEvent event) {
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;
        float viewerYaw = mc.getRenderManager().playerViewY;

        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != null)
                .filter(entity -> mc.player != entity)
                .filter(entity -> ESP.entityCheck(entity))
                .forEach(entity -> {
                    RenderUtil.prepareGL();

                    Vec3d vec3d = EntityUtil.getInterpolatedPos(entity, mc.getRenderPartialTicks());

                    GL11.glLineWidth(ESP.outlineWidth.getValue().floatValue());

                    GlStateManager.translate(vec3d.x - mc.getRenderManager().renderPosX, vec3d.y - mc.getRenderManager().renderPosY, vec3d.z - mc.getRenderManager().renderPosZ);
                    GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate((float) (isThirdPersonFrontal ? -1 : 1), 1.0f, 0.0f, 0.0f);

                    if (entity instanceof EntityEnderCrystal) {
                        GL11.glColor4f(ESP.crystalColour.getValue().getRed() / 255.0f, ESP.crystalColour.getValue().getGreen() / 255.0f, ESP.crystalColour.getValue().getBlue() / 255.0f, ESP.crystalColour.getValue().getAlpha() / 255.0f);
                    }

                    if (entity instanceof EntityPlayer) {
                        if (FriendManager.isFriend(entity.getName())) {
                            GL11.glColor4f(Social.friendColour.getValue().getRed() / 255.0f, Social.friendColour.getValue().getGreen() / 255.0f, Social.friendColour.getValue().getBlue() / 255.0f, Social.friendColour.getValue().getAlpha() / 255.0f);
                        } else if (EnemyManager.isEnemy(entity.getName())) {
                            GL11.glColor4f(Social.enemyColour.getValue().getRed() / 255.0f, Social.enemyColour.getValue().getGreen() / 255.0f, Social.enemyColour.getValue().getBlue() / 255.0f, Social.enemyColour.getValue().getAlpha() / 255.0f);
                        } else {
                            GL11.glColor4f(ESP.playerColour.getValue().getRed() / 255.0f, ESP.playerColour.getValue().getGreen() / 255.0f, ESP.playerColour.getValue().getBlue() / 255.0f, ESP.playerColour.getValue().getAlpha() / 255.0f);
                        }
                    }

                    if (entity instanceof EntityAnimal) {
                        GL11.glColor4f(ESP.animalColour.getValue().getRed() / 255.0f, ESP.animalColour.getValue().getGreen() / 255.0f, ESP.animalColour.getValue().getBlue() / 255.0f, ESP.animalColour.getValue().getAlpha() / 255.0f);
                    }

                    if (entity instanceof EntityMob) {
                        GL11.glColor4f(ESP.mobColour.getValue().getRed() / 255.0f, ESP.mobColour.getValue().getGreen() / 255.0f, ESP.mobColour.getValue().getBlue() / 255.0f, ESP.mobColour.getValue().getAlpha() / 255.0f);
                    }

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
