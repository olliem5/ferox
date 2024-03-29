package com.olliem5.ferox.api.util.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.util.text.TextComponentString;

/**
 * @author olliem5
 */

public final class MessageUtil implements Minecraft {
    private static final String prefix = ChatFormatting.GRAY + "[" + ChatFormatting.GOLD + Ferox.NAME_VERSION + ChatFormatting.GRAY + "]";

    public static void sendClientMessage(String message) {
        mc.player.sendMessage(new TextComponentString(prefix + " " + ChatFormatting.WHITE + message));
    }
}
