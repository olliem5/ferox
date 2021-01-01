package us.ferox.client.api.module;

import us.ferox.client.Ferox;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.traits.Minecraft;

import java.util.ArrayList;

public class Module implements Minecraft {
    private String name = getAnnotation().name();
    private String description = getAnnotation().description();
    private Category category = getAnnotation().category();
    private int key = getAnnotation().key();

    private boolean enabled = false;

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
    }

    public void disable() {
        enabled = false;
        Ferox.EVENT_BUS.unsubscribe(this);
        onDisable();
    }

    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
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

    public boolean isEnabled() {
        return enabled;
    }

    public void onEnable() {}

    public void onDisable() {}

    public void onUpdate() {}
}
