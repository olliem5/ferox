package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

@FeroxModule(name = "NoRender", description = "Stops the rendering of certain things", category = Category.Render)
public final class NoRender extends Module {
    public static final Setting<Boolean> caveCulling = new Setting<>("Cave Culling", "Fixes Mojang's cool bug", true);

    public NoRender() {
        this.addSettings(
                caveCulling
        );
    }
}
