package us.ferox.client.impl.modules.ferox;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.util.client.DiscordUtil;

@ModuleInfo(name = "DiscordRPC", description = "Shows off the client on discord", category = Category.FEROX)
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
