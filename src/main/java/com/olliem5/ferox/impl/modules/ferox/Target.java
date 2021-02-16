package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Target", description = "Controls how Ferox finds the global target for all modules", category = Category.Ferox)
public final class Target extends Module {
    public static final Setting<TargetModes> targetLogic = new Setting<>("Mode", "The logic used to find the target", TargetModes.Closest);
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range the target can be in", 1.0, 10.0, 15.0, 1);

    public Target() {
        this.addSettings(
                targetLogic,
                targetRange
        );

        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
    }

    public enum TargetModes {
        Closest,
        LowestHealth,
        LowestArmour
    }
}
