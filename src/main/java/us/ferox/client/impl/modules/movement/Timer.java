package us.ferox.client.impl.modules.movement;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;

@ModuleInfo(name = "Timer", description = "Changes your client side tick speed", category = Category.MOVEMENT)
public class Timer extends Module {
    public void onUpdate() {
        mc.timer.tickLength = 50f / (float) 3.7;
    }

    @Override
    public void onDisable() {
        mc.timer.tickLength = 50f;
    }
}
