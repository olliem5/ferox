package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.impl.modules.render.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Gav06
 */

@Mixin(Block.class)
public final class MixinBlock {
    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    public void renderSidePatch(IBlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Xray.doXray) {
            if (Xray.xrayBlocks.contains(blockState.getBlock())) {
                callbackInfoReturnable.setReturnValue(true);
            } else {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
    }

    @Inject(method = "isFullCube", at = @At("HEAD"), cancellable = true)
    public void fullCubePatch(IBlockState iBlockState, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Xray.doXray) {
            callbackInfoReturnable.setReturnValue(Xray.xrayBlocks.contains(iBlockState.getBlock()));
        }
    }
}