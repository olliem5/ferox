package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.world.EntityUtil;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
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
                .filter(entity -> entityCheck(entity))
                .forEach(entity -> {
                    GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.glLineWidth(ESP.outlineWidth.getValue().floatValue());
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.disableLighting();
                    GlStateManager.disableCull();
                    GlStateManager.enableAlpha();
                    GlStateManager.color(1,1,1);
                    GlStateManager.pushMatrix();

                    Vec3d vec3d = EntityUtil.getInterpolatedPos(entity, mc.getRenderPartialTicks());

                    GlStateManager.translate(vec3d.x-mc.getRenderManager().renderPosX, vec3d.y-mc.getRenderManager().renderPosY, vec3d.z-mc.getRenderManager().renderPosZ);
                    GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate((float) (isThirdPersonFrontal ? -1 : 1), 1.0f, 0.0f, 0.0f);

                    GL11.glColor4f(1, 1, 1, 0.5f);
                    GL11.glLineWidth(ESP.outlineWidth.getValue().floatValue());
                    GL11.glEnable(GL_LINE_SMOOTH);

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
                    GL11.glColor4f(1, 1, 1, 0.5f);

                    GlStateManager.enableCull();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableBlend();
                    GlStateManager.enableDepth();
                    GlStateManager.color(1,1,1);

                    GL11.glColor4f(1, 1, 1, 1);

                    GlStateManager.popMatrix();
                });

        GL11.glColor4f(1,1,1, 1);
    }

    private boolean entityCheck(Entity entity) {
        if (entity instanceof EntityEnderCrystal && ESP.crystals.getValue() || entity instanceof EntityPlayer && ESP.players.getValue() || entity instanceof EntityAnimal && ESP.animals.getValue() || entity instanceof EntityMob && ESP.mobs.getValue()) return true;

        return false;
    }
}
