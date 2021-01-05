package us.ferox.client.impl.modules.misc;

import git.littledraily.eventsystem.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.impl.events.PacketEvent;

@ModuleInfo(name = "ChatSuffix", description = "Adds a custom ending to your chat messages", category = Category.MISC)
public class ChatSuffix extends Module {
    public static Setting<Boolean> green = new Setting<>("Green", false);
    public static Setting<Boolean> blue = new Setting<>("Blue", false);

    public ChatSuffix() {
        this.addSetting(green);
        this.addSetting(blue);
    }

    private String suffix = " \u00bb \uff26\uff45\uff52\uff4f\uff58";

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
