package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.util.client.DiscordUtil;

/**
 * @author olliem5
 */

@FeroxModule(name = "DiscordRPC", description = "Shows off Ferox on discord", category = Category.Ferox)
public final class DiscordRPC extends Module {
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
