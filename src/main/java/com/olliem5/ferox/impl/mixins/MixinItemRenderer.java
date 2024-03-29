package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.impl.events.TransformFirstPersonEvent;
import com.olliem5.ferox.impl.modules.render.ViewModel;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(ItemRenderer.class)
public final class MixinItemRenderer {
    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformEatFirstPerson(float p_187454_1_, EnumHandSide enumHandSide, ItemStack itemStack, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);

        if (ModuleManager.getModuleByName("ViewModel").isEnabled() && ViewModel.cancelEating.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPersonHead(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);
    }

    @Inject(method = "transformFirstPerson", at = @At("TAIL"))
    public void transformFirstPersonTail(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Post(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);
    }

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide enumHandSide, float p_187459_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);
    }
}
