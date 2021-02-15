package me.olliem5.ferox.impl.modules.misc;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.PacketEvent;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.network.play.client.CPacketChatMessage;

/**
 * @author olliem5
 */

@FeroxModule(name = "ChatSuffix", description = "Adds a custom ending to your chat messages", category = Category.MISC)
public final class ChatSuffix extends Module {
    public static final Setting<Boolean> green = new Setting<>("Green", "Makes your suffix green", false);
    public static final Setting<Boolean> blue = new Setting<>("Blue", "Makes your suffix blue", false);

    public ChatSuffix() {
        this.addSettings(
                green,
                blue
        );
    }

    private final String suffix = " \u00bb \uff26\uff45\uff52\uff4f\uff58";

    @PaceHandler
    public void onPacket(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof CPacketChatMessage) {

            String string = ((CPacketChatMessage) event.getPacket()).getMessage();

            if (string.startsWith("/")) return;

            if (blue.getValue()) {
                string += " `" + suffix;
            } else if (green.getValue()) {
                string += " >" + suffix;
            } else {
                string += suffix;
            }

            if (string.length() >= 256) {
                string = string.substring(0, 256);
            }

            ((CPacketChatMessage) event.getPacket()).message = string;
        }
    }
}
