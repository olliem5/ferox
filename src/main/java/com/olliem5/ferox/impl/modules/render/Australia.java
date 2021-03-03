package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.util.ResourceLocation;

/**
 * @author olliem5
 */

@FeroxModule(name = "Australia", description = "Join the gang", category = Category.Render)
public final class Australia extends Module {
    public static final Setting<Boolean> fire = new Setting<>("Fire", "Shows fire on your screen", true);

    public Australia() {
        this.addSettings(
                fire
        );
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    }

    public void onUpdate() {
        if (nullCheck()) return;

        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/flip.json"));

        if (fire.getValue()) {
            mc.player.setFire(1);
        }
    }
}
