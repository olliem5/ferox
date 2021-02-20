package com.olliem5.ferox.impl.mixins;

import com.google.common.base.Predicate;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.exploit.NoEntityTrace;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author olliem5
 */

@Mixin(EntityRenderer.class)
public final class MixinEntityRenderer implements Minecraft {
    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(WorldClient worldClient, Vec3d start, Vec3d end) {
        if (ModuleManager.getModuleByName("CameraClip").isEnabled()) {
            return null;
        } else {
            return worldClient.rayTraceBlocks(start, end);
        }
    }

    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entity, AxisAlignedBB axisAlignedBB, Predicate predicate) {
        if (ModuleManager.getModuleByName("NoEntityTrace").isEnabled() && NoEntityTrace.pickaxeOnly.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
            return new ArrayList<>();
        } else if (ModuleManager.getModuleByName("NoEntityTrace").isEnabled() && !NoEntityTrace.pickaxeOnly.getValue()) {
            return new ArrayList<>();
        }

        return worldClient.getEntitiesInAABBexcluding(entity, axisAlignedBB, predicate);
    }
}
