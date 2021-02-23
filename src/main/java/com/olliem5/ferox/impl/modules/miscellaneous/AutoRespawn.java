package com.olliem5.ferox.impl.modules.miscellaneous;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.api.util.math.MathUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;

/**
 * @author olliem5
 */

@FeroxModule(name = "AutoRespawn", description = "Automatically respawns you when you die", category = Category.Miscellaneous)
public final class AutoRespawn extends Module {
    public static final Setting<Boolean> printCoordinates = new Setting<>("Print Coordinates", "Prints your death coordinates client side when you respawn", true);

    public AutoRespawn() {
        this.addSettings(
                printCoordinates
        );
    }

    @PaceHandler
    public void onGuiOpen(GuiOpenEvent event) {
        if (nullCheck()) return;

        if (event.getGui() instanceof GuiGameOver) {
            if (printCoordinates.getValue()) {
                MessageUtil.sendClientMessage("You " + ChatFormatting.RED + "died " + ChatFormatting.WHITE + "at [" + MathUtil.roundAvoid(mc.player.posX, 1) + ", " + MathUtil.roundAvoid(mc.player.posY, 1) + ", " + MathUtil.roundAvoid(mc.player.posZ, 1) + "].");
            }

            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
