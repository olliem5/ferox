package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.util.client.DiscordUtil;

@FeroxModule(name = "DiscordRPC", description = "Shows off Ferox on discord", category = Category.FEROX)
public final class DiscordRPC extends Module {
    public DiscordRPC() {
        this.setEnabled(true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        DiscordUtil.startup();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        DiscordUtil.shutdown();
    }
}
