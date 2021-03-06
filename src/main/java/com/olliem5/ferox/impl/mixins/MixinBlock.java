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
public class MixinBlock {

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    public void renderSidePatch(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        if (Xray.doXray) {
            if (Xray.xrayBlocks.contains(blockState.getBlock())) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    @Inject(method = "isFullCube", at = @At("HEAD"), cancellable = true)
    public void fullCubePatch(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (Xray.doXray) {
            cir.setReturnValue(Xray.xrayBlocks.contains(state.getBlock()));
        }
    }
}