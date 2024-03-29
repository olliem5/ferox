package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Objects;

/**
 * @author olliem5
 */

@FeroxModule(name = "Brightness", description = "Makes your game brighter", category = Category.Render)
public final class Brightness extends Module {
    public static final Setting<BrightnessModes> brightnessMode = new Setting<>("Mode", "The way of achieving brightness", BrightnessModes.Gamma);

    public Brightness() {
        this.addSettings(
                brightnessMode
        );
    }

    private final PotionEffect nightVision = new PotionEffect(Objects.requireNonNull(Potion.getPotionById(16)));

    private float originalGamma;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (brightnessMode.getValue() == BrightnessModes.Gamma) {
            originalGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 10;
        } else {
            mc.player.addPotionEffect(nightVision);
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (brightnessMode.getValue() == BrightnessModes.Gamma) {
            mc.gameSettings.gammaSetting = originalGamma;
        } else {
            mc.player.removeActivePotionEffect(nightVision.getPotion());
        }
    }

    @Override
    public String getArraylistInfo() {
        return brightnessMode.getValue().toString();
    }

    public enum BrightnessModes {
        Gamma,
        Potion
    }
}
