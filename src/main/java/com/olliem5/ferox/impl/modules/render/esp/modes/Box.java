package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

/**
 * @author olliem5
 */

public final class Box extends ESPMode {
    @Override
    public void drawESP(RenderWorldLastEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> mc.player != entity)
                .filter(ESP::entityCheck)
                .forEach(entity -> {
                    RenderUtil.prepareGL();

                    AxisAlignedBB axisAlignedBB = entity.boundingBox.offset(-mc.renderManager.renderPosX, -mc.renderManager.renderPosY, -mc.renderManager.renderPosZ);

                    GL11.glLineWidth(ESP.lineWidth.getValue().floatValue());

                    if (entity instanceof EntityEnderCrystal) {
                        RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, ESP.crystalColour.getValue().getRed() / 255.0f, ESP.crystalColour.getValue().getGreen() / 255.0f, ESP.crystalColour.getValue().getBlue() / 255.0f, ESP.crystalColour.getValue().getAlpha() / 255.0f);                    }

                    if (entity instanceof EntityPlayer) {
                        if (FriendManager.isFriend(entity.getName())) {
                            RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, Social.friendColour.getValue().getRed() / 255.0f, Social.friendColour.getValue().getGreen() / 255.0f, Social.friendColour.getValue().getBlue() / 255.0f, Social.friendColour.getValue().getAlpha() / 255.0f);
                        } else if (EnemyManager.isEnemy(entity.getName())) {
                            RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, Social.enemyColour.getValue().getRed() / 255.0f, Social.enemyColour.getValue().getGreen() / 255.0f, Social.enemyColour.getValue().getBlue() / 255.0f, Social.enemyColour.getValue().getAlpha() / 255.0f);
                        } else {
                            RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, ESP.playerColour.getValue().getRed() / 255.0f, ESP.playerColour.getValue().getGreen() / 255.0f, ESP.playerColour.getValue().getBlue() / 255.0f, ESP.playerColour.getValue().getAlpha() / 255.0f);
                        }
                    }

                    if (entity instanceof EntityAnimal) {
                        RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, ESP.animalColour.getValue().getRed() / 255.0f, ESP.animalColour.getValue().getGreen() / 255.0f, ESP.animalColour.getValue().getBlue() / 255.0f, ESP.animalColour.getValue().getAlpha() / 255.0f);
                    }

                    if (entity instanceof EntityMob) {
                        RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, ESP.mobColour.getValue().getRed() / 255.0f, ESP.mobColour.getValue().getGreen() / 255.0f, ESP.mobColour.getValue().getBlue() / 255.0f, ESP.mobColour.getValue().getAlpha() / 255.0f);
                    }

                    RenderUtil.releaseGL();
                });
    }
}
