package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.movement.Velocity;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 */

@Mixin(BlockLiquid.class)
public final class MixinBlockLiquid {
    @Inject(method = "modifyAcceleration", at = @At("HEAD"), cancellable = true)
    public void modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3d vec3d, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("Velocity").isEnabled() && Velocity.noPush.getValue() && Velocity.noPushLiquids.getValue()) {
            callbackInfoReturnable.setReturnValue(vec3d);
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method = "canCollideCheck", at = @At("HEAD"), cancellable = true)
    public void canCollideCheck(IBlockState iBlockState, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(ModuleManager.getModuleByName("LiquidInteract").isEnabled() || (hitIfLiquid && (int) iBlockState.getValue((IProperty) BlockLiquid.LEVEL) == 0));
    }
}
