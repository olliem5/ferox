package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.impl.modules.render.Xray;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Gav06
 *
 * disables cave culling when xray is enabled
 */

@Mixin(VisGraph.class)
public class MixinVisGraph {

    @Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
    public void setOpaqueCubePatch(BlockPos pos, CallbackInfo ci) {
        if (Xray.doXray)
            ci.cancel();
    }
}
