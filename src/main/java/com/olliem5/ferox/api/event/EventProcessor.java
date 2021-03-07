package com.olliem5.ferox.api.event;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.events.PacketEvent;
import com.olliem5.ferox.impl.events.TotemPopEvent;
import com.olliem5.pace.annotation.PaceHandler;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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

                String keyName = Keyboard.getKeyName(Keyboard.getEventKey());

                // if someone presses the command prefix, it will open the chat box
                // with the key prefix already typed in (not working atm :/)
                if (keyName.equalsIgnoreCase(Ferox.CHAT_PREFIX)) {
                    mc.displayGuiScreen(new GuiChat(Ferox.CHAT_PREFIX));
                }

                try {
                    Ferox.EVENT_BUS.dispatchEvent(event);
                } catch (Exception ignored) {}
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

            mc.ingameGUI.getChatGUI().addToSentMessages(event.getOriginalMessage());

            CommandManager.parseCommand(event.getMessage().replace(Ferox.CHAT_PREFIX, ""));
        } else {
            event.setMessage(event.getMessage());
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onFOVModify(EntityViewRenderEvent.FOVModifier event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onFogColours(EntityViewRenderEvent.FogColors event) {
        Ferox.EVENT_BUS.dispatchEvent(event);
    }

    @PaceHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);

                Ferox.EVENT_BUS.dispatchPaceEvent(new TotemPopEvent(entity));
            }
        }
    }
}
