package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.modules.render.NoRender;
import com.olliem5.ferox.impl.modules.render.Xray;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VisGraph.class)
public final class MixinVisGraph {

    @Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
    public void setOpaqueCubePatch(BlockPos blockPos, CallbackInfo callbackInfo) {
        if (Xray.doXray || ModuleManager.getModuleByName("NoRender").isEnabled() && NoRender.caveCulling.getValue()) {
            callbackInfo.cancel();
        }
    }
}
