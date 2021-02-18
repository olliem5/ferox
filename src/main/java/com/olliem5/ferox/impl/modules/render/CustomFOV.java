package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;

/**
 * @author olliem5
 *
 * TODO: Make the FOV reset
 */

@FeroxModule(name = "CustomFOV", description = "Allows you to change your normal and item fov", category = Category.Render)
public final class CustomFOV extends Module {
    public static final Setting<Boolean> normalFOV = new Setting<>("Normal FOV", "Allows for modification of your normal FOV", true);
    public static final NumberSetting<Integer> normalFOVAmount = new NumberSetting<>(normalFOV, "Normal FOV Amount", "The value to set your normal FOV to", 0, 120, 250, 0);

    public static final Setting<Boolean> itemFOV = new Setting<>("Item FOV", "Allows for modification of your item FOV", true);
    public static final NumberSetting<Integer> itemFOVAmount = new NumberSetting<>(itemFOV, "Item FOV Amount", "The value to set your item FOV to", 0, 120, 250, 0);

    public CustomFOV() {
        this.addSettings(
                normalFOV,
                itemFOV
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (normalFOV.getValue()) {
            Minecraft.mc.gameSettings.fovSetting = normalFOVAmount.getValue();
        }
    }

    @PaceHandler
    public void onFOVModify(EntityViewRenderEvent.FOVModifier event) {
        if (nullCheck()) return;

        if (itemFOV.getValue()) {
            event.setFOV(itemFOVAmount.getValue());
        }
    }
}
