package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.movement.NoSlow;
import net.minecraft.block.BlockSlime;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(BlockSlime.class)
public final class MixinBlockSlime {
    @Inject(method = "onEntityWalk", at = @At("HEAD"), cancellable = true)
    private void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("NoSlow").isEnabled() && NoSlow.blocks.getValue() && NoSlow.slimeBlocks.getValue()) {
            callbackInfo.cancel();
        }
    }
}
