package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.util.client.DiscordUtil;

@ModuleInfo(name = "DiscordRPC", description = "Shows off Ferox on discord", category = Category.FEROX)
public class DiscordRPC extends Module {
    public DiscordRPC() {
        this.setEnabled(true);
    }

    @Override
    public void onEnable() {
        DiscordUtil.startup();
    }

    @Override
    public void onDisable() {
        DiscordUtil.shutdown();
    }
}
