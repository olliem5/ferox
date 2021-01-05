package us.ferox.client.api.util.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import us.ferox.client.Ferox;
import us.ferox.client.api.traits.Minecraft;

public class MessageUtil implements Minecraft {
    public static String prefix = ChatFormatting.GRAY + "[" + ChatFormatting.GOLD + Ferox.NAME_VERSION + ChatFormatting.GRAY + "]";

    public static void sendClientMessage(String message) {
        mc.player.sendMessage(new TextComponentString(prefix + " " + ChatFormatting.WHITE + message));
    }
}
