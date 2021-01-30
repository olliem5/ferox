package us.ferox.client.api.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import us.ferox.client.Ferox;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.api.util.client.MessageUtil;
import us.ferox.client.impl.modules.ferox.Notifier;

import java.util.ArrayList;

public class Module implements Minecraft {
    private String name = getAnnotation().name();
    private String description = getAnnotation().description();
    private Category category = getAnnotation().category();
    private int key = getAnnotation().key();

    private boolean enabled = false;
    private boolean opened = false;
    private boolean binding = false;

    private ArrayList<Setting> settings = new ArrayList<>();

    private ModuleInfo getAnnotation() {
        if (getClass().isAnnotationPresent(ModuleInfo.class)) {
            return getClass().getAnnotation(ModuleInfo.class);
        }
        throw new IllegalStateException("Annotation 'ModuleInfo' not found!");
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void enable() {
        enabled = true;
        Ferox.EVENT_BUS.subscribe(this);
        onEnable();

        if (mc.world != null) {
            if (Ferox.moduleManager.getModuleByName("Notifier").isEnabled() && Notifier.moduleToggle.getValue()) {
                if (name != "ClickGUI" && name != "HudEditor") {
                    MessageUtil.sendClientMessage("Module " + ChatFormatting.AQUA + name + ChatFormatting.WHITE + " has been " + ChatFormatting.GREEN + "ENABLED");
                }
            }
        }
    }

    public void disable() {
        enabled = false;
        Ferox.EVENT_BUS.unsubscribe(this);
        onDisable();

        if (mc.world != null) {
            if (Ferox.moduleManager.getModuleByName("Notifier").isEnabled() && Notifier.moduleToggle.getValue()) {
                if (name != "ClickGUI" && name != "HudEditor") {
                    MessageUtil.sendClientMessage("Module " + ChatFormatting.AQUA + name + ChatFormatting.WHITE + " has been " + ChatFormatting.RED + "DISABLED");
                }
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

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void onEnable() {}

    public void onDisable() {}

    public void onUpdate() {}
}
