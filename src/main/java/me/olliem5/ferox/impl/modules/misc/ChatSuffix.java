package me.olliem5.ferox.impl.modules.misc;

import git.littledraily.eventsystem.Listener;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.PacketEvent;
import net.minecraft.network.play.client.CPacketChatMessage;

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

    @Listener
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {

            String s = ((CPacketChatMessage) event.getPacket()).getMessage();

            if (s.startsWith("/")) return;

            if (blue.getValue()) {
                s += " `" + suffix;
            } else if (green.getValue()) {
                s += " >" + suffix;
            } else {
                s += suffix;
            }

            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }

            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    }
}
