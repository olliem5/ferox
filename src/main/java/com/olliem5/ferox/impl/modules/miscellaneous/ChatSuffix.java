package com.olliem5.ferox.impl.modules.miscellaneous;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.events.PacketEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.network.play.client.CPacketChatMessage;

/**
 * @author olliem5
 */

@FeroxModule(name = "ChatSuffix", description = "Adds a custom ending to your chat messages", category = Category.Miscellaneous)
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
            CPacketChatMessage cPacketChatMessage = (CPacketChatMessage) event.getPacket();

            String string = cPacketChatMessage.getMessage();

            if (string.startsWith(Ferox.CHAT_PREFIX) || string.startsWith("/")) return;

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

            cPacketChatMessage.message = string;
        }
    }
}
