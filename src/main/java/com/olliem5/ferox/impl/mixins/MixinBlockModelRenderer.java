package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.impl.modules.render.Xray;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Gav06
 */

@Mixin(BlockModelRenderer.class)
public final class MixinBlockModelRenderer {
    @Inject(method = "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z", at = @At("HEAD"), cancellable = true)
    public void renderModelPatch(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, BufferBuilder bufferBuilder, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Xray.doXray) {
            if (!Xray.xrayBlocks.contains(iBlockState.getBlock())) {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
    }

    @Inject(method = "renderModelSmooth", at = @At("HEAD"), cancellable = true)
    public void renderModelSmoothPatch(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, BufferBuilder bufferBuilder, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Xray.doXray) {
            if (!Xray.xrayBlocks.contains(iBlockState.getBlock())) {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
    }
}
