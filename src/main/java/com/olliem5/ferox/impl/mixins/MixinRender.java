package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.ESP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

/**
 * @author olliem5
 */

@Mixin(Render.class)
public final class MixinRender<T extends Entity> {
    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    public void getTeamColour(T entity, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("ESP").isEnabled()) {
            callbackInfoReturnable.cancel();

            if (entity instanceof EntityEnderCrystal) {
                callbackInfoReturnable.setReturnValue(new Color(ESP.crystalColour.getValue().getRed(), ESP.crystalColour.getValue().getGreen(), ESP.crystalColour.getValue().getBlue(), ESP.crystalColour.getValue().getAlpha()).getRGB());
            }

            if (entity instanceof EntityPlayer) {
                if (FriendManager.isFriend(entity.getName())) {
                    callbackInfoReturnable.setReturnValue(new Color(Social.friendColour.getValue().getRed(), Social.friendColour.getValue().getGreen(), Social.friendColour.getValue().getBlue(), Social.friendColour.getValue().getAlpha()).getRGB());
                } else if (EnemyManager.isEnemy(entity.getName())) {
                    callbackInfoReturnable.setReturnValue(new Color(Social.enemyColour.getValue().getRed(), Social.enemyColour.getValue().getGreen(), Social.enemyColour.getValue().getBlue(), Social.enemyColour.getValue().getAlpha()).getRGB());
                } else {
                    callbackInfoReturnable.setReturnValue(new Color(ESP.playerColour.getValue().getRed(), ESP.playerColour.getValue().getGreen(), ESP.playerColour.getValue().getBlue(), ESP.playerColour.getValue().getAlpha()).getRGB());
                }
            }

            if (entity instanceof EntityAnimal) {
                callbackInfoReturnable.setReturnValue(new Color(ESP.animalColour.getValue().getRed(), ESP.animalColour.getValue().getGreen(), ESP.animalColour.getValue().getBlue(), ESP.animalColour.getValue().getAlpha()).getRGB());
            }

            if (entity instanceof EntityMob) {
                callbackInfoReturnable.setReturnValue(new Color(ESP.mobColour.getValue().getRed(), ESP.mobColour.getValue().getGreen(), ESP.mobColour.getValue().getBlue(), ESP.mobColour.getValue().getAlpha()).getRGB());
            }
        }
    }
}
