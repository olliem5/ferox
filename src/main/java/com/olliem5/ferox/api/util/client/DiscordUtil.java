package com.olliem5.ferox.api.util.client;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

/**
 * @author olliem5
 */

public final class DiscordUtil implements Minecraft {
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static void startup() {
        Ferox.log("Discord RPC is starting up!");

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize(Ferox.APP_ID, handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "ferox";
        discordRichPresence.largeImageText = "Ferox strong client";

        discordRPC.Discord_UpdatePresence(discordRichPresence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "Version " + Ferox.MOD_VERSION;
                    state = "Main Menu";

                    if (mc.isIntegratedServerRunning()) {
                        state = "Singleplayer | " + mc.getIntegratedServer().getWorldName();
                    } else if (mc.currentScreen instanceof GuiMultiplayer) {
                        state = "Multiplayer Menu";
                    } else if (mc.currentScreen instanceof GuiWorldSelection) {
                        state = "Singleplayer Menu";
                    } else if (mc.getCurrentServerData() != null) {
                        state = "Server | " + mc.getCurrentServerData().serverIP.toLowerCase();
                    }

                    discordRichPresence.details = details;
                    discordRichPresence.state = state;

                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } try {
                    Thread.sleep(5000L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        Ferox.log("Discord RPC is shutting down!");

        discordRPC.Discord_Shutdown();
    }
}
