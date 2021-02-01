package me.olliem5.ferox.api.event;

import git.littledraily.eventsystem.Listener;
import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.impl.events.*;
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

public class EventProcessor implements Minecraft {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.world != null) {
            for (Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    module.onUpdate();
                }
            }

            Ferox.EVENT_BUS.post(new UpdateEvent());
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (mc.world != null) {
            Ferox.EVENT_BUS.post(new WorldRenderEvent(event.getContext(), event.getPartialTicks()));
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (event.isCanceled()) return;

        Ferox.EVENT_BUS.post(new GameOverlayRenderEvent());
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatEvent event) {
        if (event.getMessage().startsWith(Ferox.CHAT_PREFIX)) {
            event.setCanceled(true);

            CommandManager.parseCommand(event.getMessage().replace(Ferox.CHAT_PREFIX, ""));
        } else {
            ChatIncomingEvent chatIncomingEvent = new ChatIncomingEvent(event.getMessage());

            Ferox.EVENT_BUS.post(chatIncomingEvent);

            if (chatIncomingEvent.isCancelled()) {
                event.setCanceled(true);
            } else {
                event.setMessage(chatIncomingEvent.getMessage());
            }
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                for (Module module : ModuleManager.getModules()) {
                    if (module.getKey() == Keyboard.getEventKey()) {
                        module.toggle();
                    }
                }
            }

            Ferox.EVENT_BUS.post(new KeyPressedEvent(Keyboard.getEventKey()));
        }
    }

    @Listener
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);

                if (entity == null) return;

                Ferox.EVENT_BUS.post(new TotemPopEvent(entity));
            }
        }
    }
}
