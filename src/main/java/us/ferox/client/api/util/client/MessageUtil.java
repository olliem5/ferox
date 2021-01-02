package us.ferox.client.api.util.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import us.ferox.client.Ferox;
import us.ferox.client.api.traits.Minecraft;

public class MessageUtil implements Minecraft {
    private static final EntityPlayerSP entityPlayerSP = mc.player;

    public static String prefix = ChatFormatting.GRAY + "[" + ChatFormatting.GOLD + Ferox.NAME_VERSION + ChatFormatting.GRAY + "]";

    public static void sendRawMessage(String message) {
        entityPlayerSP.sendMessage(new TextComponentString(message));
    }

    public static void sendClientMessage(String message) {
        sendRawMessage(prefix + " " + ChatFormatting.WHITE + message);
    }
}
