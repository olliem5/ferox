package com.olliem5.ferox.impl.modules.movement;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author olliem5
 */

@FeroxModule(name = "NoSlow", description = "Prevents using items from slowing you down", category = Category.Movement)
public final class NoSlow extends Module {
    public static final Setting<Boolean> blocks = new Setting<>("Blocks", "Allows NoSlow to stop blocks from slowing you down", true);
    public static final Setting<Boolean> soulSand = new Setting<>(blocks, "Soul Sand", "Allows soul sand slowness to cease", true);
    public static final Setting<Boolean> slimeBlocks = new Setting<>(blocks, "Slime Blocks", "Allows slime block slowness to cease", true);

    public static final Setting<Boolean> guiMove = new Setting<>("GUI Move", "Allows you to move in GUI's", true);
    public static final Setting<Boolean> arrowKeyLook = new Setting<>(guiMove, "Arrow Key Look", "Allows you to look around with the arrow keys", true);

    public NoSlow() {
        this.addSettings(
                blocks,
                guiMove
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (guiMove.getValue()) {
            if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) && guiMove.getValue()) {
                if (arrowKeyLook.getValue()) {
                    if (Keyboard.isKeyDown(200)) {
                        mc.player.rotationPitch -= 5;
                    }

                    if (Keyboard.isKeyDown(208)) {
                        mc.player.rotationPitch += 5;
                    }

                    if (Keyboard.isKeyDown(205)) {
                        mc.player.rotationYaw += 5;
                    }

                    if (Keyboard.isKeyDown(203)) {
                        mc.player.rotationYaw -= 5;
                    }

                    if (mc.player.rotationPitch > 90) {
                        mc.player.rotationPitch = 90;
                    }

                    if (mc.player.rotationPitch < -90) {
                        mc.player.rotationPitch = -90;
                    }
                }
            }
        }
    }

    @PaceHandler
    public void onInputUpdate(InputUpdateEvent event) {
        if (nullCheck()) return;

        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }
}
