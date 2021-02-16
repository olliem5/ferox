package com.olliem5.ferox.api.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.notification.Notification;
import com.olliem5.ferox.api.notification.NotificationManager;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.impl.modules.ferox.Notifications;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.client.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author olliem5
 */

public abstract class Module implements Minecraft {
    private final String name = getAnnotation().name();
    private final String description = getAnnotation().description();
    private final Category category = getAnnotation().category();

    private int key = getAnnotation().key();

    private boolean enabled = false;
    private boolean opened = false;
    private boolean binding = false;

    private final ArrayList<Setting> settings = new ArrayList<>();

    private FeroxModule getAnnotation() {
        if (getClass().isAnnotationPresent(FeroxModule.class)) {
            return getClass().getAnnotation(FeroxModule.class);
        }

        throw new IllegalStateException("Annotation 'FeroxModule' not found!");
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void enable() {
        enabled = true;

        Ferox.EVENT_BUS.register(this);

        onEnable();

        handleNotifications(true);
    }

    public void disable() {
        enabled = false;

        Ferox.EVENT_BUS.unregister(this);

        onDisable();

        handleNotifications(false);
    }

    private void handleNotifications(boolean enable) {
        if (nullCheck()) return;

        String message;

        if (enable) {
            message = ChatFormatting.AQUA + name + ChatFormatting.WHITE + " has been " + ChatFormatting.GREEN + "ENABLED";
        } else {
            message = ChatFormatting.AQUA + name + ChatFormatting.WHITE + " has been " + ChatFormatting.RED + "DISABLED";
        }

        if (ModuleManager.getModuleByName("Notifications").isEnabled() && Notifications.moduleToggle.getValue()) {
            if (Notifications.moduleToggleRender.getValue()) {
                NotificationManager.queueNotification(new Notification("Module Toggle", message, Notification.NotificationType.Normal));
            }

            if (Notifications.moduleToggleChat.getValue()) {
                MessageUtil.sendClientMessage(message);
            }
        }
    }

    public void toggle() {
        try {
            if (enabled) {
                disable();
            } else {
                enable();
            }
        } catch (Exception e) {}
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getKey() {
        return key;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public Setting addSetting(Setting setting) {
        settings.add(setting);

        return setting;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getArraylistInfo() {
        return "";
    }

    public void onEnable() {}

    public void onDisable() {}

    public void onUpdate() {}
}
