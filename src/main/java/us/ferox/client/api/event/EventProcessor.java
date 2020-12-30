package us.ferox.client.api.event;

import git.littledraily.eventsystem.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import us.ferox.client.Ferox;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.impl.events.*;

public class EventProcessor implements Minecraft {
    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
        Ferox.EVENT_BUS.subscribe(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.world != null) {
            for (Module module : Ferox.moduleManager.getModules()) {
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
    public void onRender(RenderGameOverlayEvent event) {
        if (event.isCanceled()) return;
        Ferox.EVENT_BUS.post(new GameOverlayRenderEvent());
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Ferox.EVENT_BUS.post(new KeyPressedEvent(Keyboard.getEventKey()));
            for (Module module : Ferox.moduleManager.getModules()) {
                if (module.getKey() == Keyboard.KEY_NONE) return;
                if (module.getKey() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
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
