package me.olliem5.ferox.api.util.client;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

public class DiscordUtil implements Minecraft {
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence rp = new DiscordRichPresence();
    private static String details;
    private static String state;

    public static void startup() {
        Ferox.log("Discord RPC is starting up!");
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize(Ferox.APP_ID, handlers, true, "");
        rp.startTimestamp = System.currentTimeMillis() / 1000L;
        rp.largeImageKey = "ferox";
        rp.largeImageText = "Ferox strong client";
        rpc.Discord_UpdatePresence(rp);

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

                    rp.details = details;
                    rp.state = state;
                    rpc.Discord_UpdatePresence(rp);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        Ferox.log("Discord RPC is shutting down!");
        rpc.Discord_Shutdown();
    }
}
