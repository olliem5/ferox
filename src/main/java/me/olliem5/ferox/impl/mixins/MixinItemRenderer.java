package me.olliem5.ferox.impl.mixins;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.impl.events.TransformSideFirstPersonEvent;
import me.olliem5.ferox.impl.modules.render.ViewModel;
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
        TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);

        if (ModuleManager.getModuleByName("ViewModel").isEnabled() && ViewModel.cancelEating.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPerson(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);
    }

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide enumHandSide, float p_187459_2_, CallbackInfo callbackInfo) {
        TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(enumHandSide);
        Ferox.EVENT_BUS.dispatchPaceEvent(event);
    }
}
