package me.olliem5.ferox.impl.modules.render;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;
import me.olliem5.ferox.api.setting.Setting;

@ModuleInfo(name = "Brightness", description = "Makes your game brighter", category = Category.RENDER)
public class Brightness extends Module {
    public static Setting<BrightnessModes> brightnessMode = new Setting<>("Mode", BrightnessModes.Gamma);

    public Brightness() {
        this.addSetting(brightnessMode);
    }

    private PotionEffect nightVision = new PotionEffect(Potion.getPotionById(16));

    private float originalGamma;

    @Override
    public void onEnable() {
        if (brightnessMode.getValue() == BrightnessModes.Gamma) {
            originalGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 10;
        } else {
            mc.player.addPotionEffect(nightVision);
        }
    }

    @Override
    public void onDisable() {
        if (brightnessMode.getValue() == BrightnessModes.Gamma) {
            mc.gameSettings.gammaSetting = originalGamma;
        } else {
            mc.player.removeActivePotionEffect(nightVision.getPotion());
        }
    }

    public enum BrightnessModes {
        Gamma,
        Potion
    }
}
