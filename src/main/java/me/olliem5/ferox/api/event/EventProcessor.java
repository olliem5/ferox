package me.olliem5.ferox.api.event;

import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.events.PacketEvent;
import me.olliem5.ferox.impl.events.TotemPopEvent;
import me.olliem5.pace.annotation.PaceHandler;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author olliem5
 */

public final class EventProcessor implements Minecraft {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                for (Module module : ModuleManager.getModules()) {
                    if (module.getKey() == Keyboard.getEventKey()) {
                        module.toggle();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (Module module : ModuleManager.getModules()) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(Ferox.CHAT_PREFIX)) {
            event.setCanceled(true);

            CommandManager.parseCommand(event.getMessage().replace(Ferox.CHAT_PREFIX, ""));
        } else {
            event.setMessage(event.getMessage());
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Ferox.EVENT_BUS.dispatchEvent(event); //This is throwing weird fucky NPE's and spamming the output. TODO: Look into this!
    }

    @SubscribeEvent
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @PaceHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);

                if (entity != null) {
                    Ferox.EVENT_BUS.dispatchPaceEvent(new TotemPopEvent(entity));
                }
            }
        }
    }
}
