package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "Biome", description = "Displays the biome you are currently in")
public class BiomeComponent extends Component {
    public static final Setting<BiomeModes> biomeMode = new Setting<>("Mode", "The way of displaying the biome", BiomeModes.Normal);

    public BiomeComponent() {
        this.addSettings(
                biomeMode
        );
    }

    @Override
    public void render() {
        String renderString;

        switch (biomeMode.getValue()) {
            case Normal:
                renderString = "Biome " + ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "" + ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    public enum BiomeModes {
        Normal,
        Short
    }
}
