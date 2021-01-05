package us.ferox.client.impl.modules.render;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.Setting;

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
