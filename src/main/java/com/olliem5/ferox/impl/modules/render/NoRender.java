package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

@FeroxModule(name = "NoRender", description = "Stops rendering stuff", category = Category.Render)
public class NoRender extends Module {

    public static final Setting<Boolean> caveCulling = new Setting<>("CaveCulling", "stops mojang's stupid crap", true);

    public NoRender() {
        this.addSettings(caveCulling);
    }
}
