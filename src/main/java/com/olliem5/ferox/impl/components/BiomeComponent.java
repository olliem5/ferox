package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 */


@FeroxComponent(name = "Biome",description = "Displays the biome you are currently in on your screen")
public class BiomeComponent extends Component {

    Setting<BiomeModes> biomemode = new Setting("Mode","Mode",BiomeModes.Normal);

    public BiomeComponent() {
        addSettings(
                biomemode
        );
    }

    @Override
    public void render() {
        String BiomeText;

        switch ((BiomeModes) biomemode.getValue()) {
            case Normal: {
                BiomeText = "Biome " + ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
                drawString(BiomeText);
                this.setHeight((int) FontUtil.getStringHeight(BiomeText));
                this.setWidth((int) FontUtil.getStringWidth(BiomeText));
                break;
            }
            case OnlyBiome: {
                BiomeText = "" + ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
                drawString(BiomeText);
                this.setWidth((int) FontUtil.getStringWidth(BiomeText));
                this.setHeight((int) FontUtil.getStringHeight(BiomeText));
                break;
            }
        }

    }

    public enum BiomeModes {
        Normal, OnlyBiome
    }
}
