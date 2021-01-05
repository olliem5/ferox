package us.ferox.client.api.util.minecraft;

import net.minecraft.util.text.TextComponentString;
import us.ferox.client.api.traits.Minecraft;

public class ChatUtil implements Minecraft {
    public static String prefix = " | Ferox";

    public static void sendPrefixMessage(String message) {
        mc.player.sendMessage(new TextComponentString(prefix = message));
    }
}
