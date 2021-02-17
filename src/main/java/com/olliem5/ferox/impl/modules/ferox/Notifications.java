package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Notifications", description = "Handles Ferox's notifications client-wide", category = Category.Ferox)
public final class Notifications extends Module {
    public static final Setting<Boolean> moduleToggle = new Setting<>("Module Toggle", "Notifies you when a module is toggled", true);
    public static final Setting<Boolean> moduleToggleChat = new Setting<>(moduleToggle, "Chat", "Sends module toggle messages in chat", true);
    public static final Setting<Boolean> moduleToggleRender = new Setting<>(moduleToggle, "Render", "Renders module toggle notifications in the HUD", true);

    public Notifications() {
        this.addSettings(
                moduleToggle
        );
    }
}
