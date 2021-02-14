package me.olliem5.ferox.impl.mixins;

import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.impl.modules.render.ItemTooltips;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

@Mixin(GuiScreen.class)
public final class MixinGuiScreen {
    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack itemStack, int x, int y, CallbackInfo callbackInfo) {
        if (ModuleManager.getModuleByName("ItemTooltips").isEnabled() && ItemTooltips.shulkers.getValue() && itemStack.getItem() instanceof ItemShulkerBox) {
            if (!itemStack.isEmpty() && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
                if (itemStack.getTagCompound().getCompoundTag("BlockEntityTag").hasKey("Items", 9)) {
                    callbackInfo.cancel();
                    ItemTooltips.renderShulkerTooltip(itemStack, x + 6, y - 33);
                }
            }
        }

        if (ModuleManager.getModuleByName("ItemTooltips").isEnabled() && ItemTooltips.maps.getValue() && itemStack.getItem() instanceof ItemMap) {
            if (!itemStack.isEmpty) {
//                callbackInfo.cancel();
//                ItemTooltips.renderMapTooltip(itemStack, x, y);
            }
        }
    }
}
