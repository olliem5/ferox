package com.olliem5.ferox.impl.mixins;

import com.olliem5.ferox.impl.modules.movement.NoSlow;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author olliem5
 */

@Mixin(value = MovementInputFromOptions.class)
public final class MixinMovementInputFromOptions extends MovementInput implements Minecraft {
    @Redirect(method = "updatePlayerMoveState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(KeyBinding keyBinding) {
        if (ModuleManager.getModuleByName("NoSlow").isEnabled() && NoSlow.guiMove.getValue() && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return Keyboard.isKeyDown(keyBinding.getKeyCode());
        }

        return keyBinding.isKeyDown();
    }
}
