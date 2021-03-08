package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
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

        if (OpenGlHelper.shadersSupported) {
            try {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            } catch (Exception ignored) {}
        }
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (OpenGlHelper.shadersSupported) {
            try {
                if (mc.getRenderViewEntity() == mc.player) {
                    if (mc.entityRenderer.getShaderGroup() != null) {
                        mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                    }

                    mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/flip.json"));
                }
            } catch (Exception ignored) {}
        }

        if (fire.getValue()) {
            mc.player.setFire(1);
        }
    }
}
