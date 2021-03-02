package com.olliem5.ferox.impl.modules.render.esp.modes;

import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.impl.modules.render.ESP;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
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
                .forEach(entity -> {
                    if (entity instanceof EntityEnderCrystal) {
                        entity.setGlowing(true);
                    }

                    if (entity instanceof EntityPlayer) {
                        if (FriendManager.isFriend(entity.getName())) {
                            entity.setGlowing(true);
                        } else if (EnemyManager.isEnemy(entity.getName())) {
                            entity.setGlowing(true);
                        } else {
                            entity.setGlowing(true);
                        }
                    }

                    if (entity instanceof EntityAnimal) {
                        entity.setGlowing(true);
                    }

                    if (entity instanceof EntityMob) {
                        entity.setGlowing(true);
                    }
                });
    }
}
