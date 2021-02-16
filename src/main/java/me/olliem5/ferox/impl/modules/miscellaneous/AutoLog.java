package me.olliem5.ferox.impl.modules.miscellaneous;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;

/**
 * @author olliem5
 */

@FeroxModule(name = "AutoLog", description = "Leaves the server you are on at a specified health", category = Category.Miscellaneous)
public final class AutoLog extends Module {
    public static final Setting<LogModes> logMode = new Setting<>("Mode", "How the module makes you disconnect from the server", LogModes.Disconnect);
    public static final Setting<Boolean> toggleAfter = new Setting<>("Toggle After", "Toggles the module after a disconnect happens", true);
    public static final NumberSetting<Float> logHealth = new NumberSetting<>("Health", "The health to be on to be disconnected", 0.0f, 10.0f, 36.0f, 1);

    public AutoLog() {
        this.addSettings(
                logMode,
                toggleAfter,
                logHealth
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.getHealth() <= logHealth.getValue()) {
            switch (logMode.getValue()) {
                case Disconnect:
                    mc.world.sendQuittingDisconnectingPacket();
                    mc.loadWorld(null);
                    mc.displayGuiScreen(new GuiMainMenu());
                    break;
                case Kick:
                    mc.player.inventory.currentItem = 69420;
                    break;
            }

            if (toggleAfter.getValue()) {
                this.toggle();
            }
        }
    }

    public String getArraylistInfo() {
        return logMode.getValue().toString() + ", " + logHealth.getValue().toString();
    }

    public enum LogModes {
        Disconnect,
        Kick
    }
}
