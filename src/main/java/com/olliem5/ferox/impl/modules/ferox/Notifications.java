package com.olliem5.ferox.impl.modules.ferox;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mojang.text2speech.Narrator;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.notification.Notification;
import com.olliem5.ferox.api.notification.NotificationManager;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.impl.events.TotemPopEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author olliem5
 */

@FeroxModule(name = "Notifications", description = "Handles Ferox's notifications client-wide", category = Category.Ferox)
public final class Notifications extends Module {
    public static final Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", "Notifies you when a module is toggled", true);
    public static final Setting<Boolean> moduleToggleChat = new Setting<>(moduleToggle, "Chat", "Sends module toggle messages in chat", true);
    public static final Setting<Boolean> moduleToggleRender = new Setting<>(moduleToggle, "Render", "Renders module toggle notifications in the HUD", true);
    public static final Setting<Boolean> moduleToggleNarrate = new Setting<>(moduleToggle, "Narrate", "Narrates module toggle notifications", false);

    public static final Setting<Boolean> totemPop = new Setting<>("Totem Pop", "Notifies you when a player pops a totem", true);
    public static final Setting<Boolean> totemPopChat = new Setting<>(totemPop, "Chat", "Sends totem pop messages in chat", true);
    public static final Setting<Boolean> totemPopRender = new Setting<>(totemPop, "Render", "Renders totem pop notifications in the HUD", true);
    public static final Setting<Boolean> totemPopNarrate = new Setting<>(totemPop, "Narrate", "Narrates totem pop notifications", false);

    public Notifications() {
        this.addSettings(
                moduleToggle,
                totemPop
        );
    }

    private final Map<String, Integer> totemPopMap = new HashMap<>();

    @PaceHandler
    public void onTotemPop(TotemPopEvent event) {
        if (nullCheck()) return;

        int pops = totemPopMap.getOrDefault(event.getEntity().getName(), 0) + 1;

        totemPopMap.put(event.getEntity().getName(), pops);

        String message;
        String narratorMessage;

        if (FriendManager.isFriend(event.getEntity().getName())) {
            message = ChatFormatting.GREEN + "Your friend, " + ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " popped " + ChatFormatting.RED + pops + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            narratorMessage = "Your friend, " + event.getEntity().getName() + " popped " + pops + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
        } else if (EnemyManager.isEnemy(event.getEntity().getName())) {
            message = ChatFormatting.RED + "Your enemy, " + ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " popped " + ChatFormatting.RED + pops + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            narratorMessage = "Your enemy, " + event.getEntity().getName() + " popped " + pops + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
        } else {
            message = ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " popped " + ChatFormatting.RED + pops + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            narratorMessage = event.getEntity().getName() + " popped " + pops + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
        }

        if (totemPopChat.getValue()) {
            MessageUtil.sendClientMessage(message);
        }

        if (totemPopRender.getValue()) {
            NotificationManager.queueNotification(new Notification("Totem Pop", message, Notification.NotificationType.Info));
        }

        if (totemPopNarrate.getValue()) {
            Narrator narrator = Narrator.getNarrator();
            narrator.say(narratorMessage);
        }
    }

    @PaceHandler
    public void onLivingDeath(LivingDeathEvent event) {
        if (totemPopMap.containsKey(event.getEntity().getName())) {
            String message;
            String narratorMessage;

            if (FriendManager.isFriend(event.getEntity().getName())) {
                message = ChatFormatting.GREEN + "Your friend, " + ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " died after popping " + ChatFormatting.RED + totemPopMap.get(event.getEntity().getName()) + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
                narratorMessage = "Your friend, " + event.getEntity().getName() + " died after popping " + totemPopMap.get(event.getEntity().getName()) + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            } else if (EnemyManager.isEnemy(event.getEntity().getName())) {
                message = ChatFormatting.RED + "Your enemy, " + ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " died after popping " + ChatFormatting.RED + totemPopMap.get(event.getEntity().getName()) + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
                narratorMessage = "Your enemy, " + event.getEntity().getName() + " died after popping " + totemPopMap.get(event.getEntity().getName()) + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            } else {
                message = ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " died after popping " + ChatFormatting.RED + totemPopMap.get(event.getEntity().getName()) + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
                narratorMessage = event.getEntity().getName() + " died after popping " + totemPopMap.get(event.getEntity().getName()) + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");
            }

            if (totemPopChat.getValue()) {
                MessageUtil.sendClientMessage(message);
            }

            if (totemPopRender.getValue()) {
                NotificationManager.queueNotification(new Notification("Final Totem Pop", message, Notification.NotificationType.Info));
            }

            if (totemPopNarrate.getValue()) {
                Narrator narrator = Narrator.getNarrator();
                narrator.say(narratorMessage);
            }

            totemPopMap.put(event.getEntity().getName(), 0);
        }
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.ticksExisted % 10 != 0) return;

        totemPopMap.keySet().forEach(entity -> {
            Optional<EntityPlayer> optionalEntityPlayer = mc.world.playerEntities.stream()
                    .filter(entityPlayer -> entityPlayer.getName().equals(entity))
                    .findFirst();

            if (optionalEntityPlayer.isPresent()) {
                EntityPlayer entityPlayer = optionalEntityPlayer.get();

                if (entityPlayer.isDead || entityPlayer.getHealth() <= 0) {
                    totemPopMap.put(entity, 0);
                }
            }
        });
    }
}
