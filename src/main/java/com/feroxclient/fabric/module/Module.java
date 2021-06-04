package com.feroxclient.fabric.module;

import com.feroxclient.fabric.FeroxMod;
import com.feroxclient.fabric.setting.Setting;
import com.feroxclient.fabric.util.MinecraftTrait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module implements MinecraftTrait {

    private ModuleManifest getAnnotation() {
        if (getClass().isAnnotationPresent(ModuleManifest.class)) {
            return getClass().getAnnotation(ModuleManifest.class);
        }
        throw new IllegalStateException("No annotation in class " + getClass().getSimpleName() + "!");
    }

    private String name = getAnnotation().name();
    private final String description = getAnnotation().description();
    private final Category category = getAnnotation().category();
    private int key = getAnnotation().key();

    private final List<Setting> settings = new ArrayList<>();

    private boolean enabled = false;

    public void toggle() {
        enabled = !enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {
        FeroxMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        FeroxMod.EVENT_BUS.unsubscribe(this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public Setting addSetting(Setting setting){
        this.settings.add(setting);
        return setting;
    }
}
