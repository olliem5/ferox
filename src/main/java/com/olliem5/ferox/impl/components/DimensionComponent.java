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

@FeroxComponent(name = "Dimension", description = "Shows the dimension you are currently in")
public final class DimensionComponent extends Component {
    public static final Setting<DimensionModes> dimensionMode = new Setting<>("Mode", "The way of displaying the biome", DimensionModes.Normal);

    @Override
    public void render() {
        String renderString;

        if (mc.player.dimension == -1) {
            switch (dimensionMode.getValue()) {
                case Normal:
                    renderString = "Dimension " + ChatFormatting.WHITE + "Nether";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + "Nether";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
            }
        } else if (mc.player.dimension == 0) {
            switch (dimensionMode.getValue()) {
                case Normal:
                    renderString = "Dimension " + ChatFormatting.WHITE + "Overworld";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + "Overworld";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
            }
        } else {
            switch (dimensionMode.getValue()) {
                case Normal:
                    renderString = "Dimension " + ChatFormatting.WHITE + "End";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
                case Short:
                    renderString = "" + ChatFormatting.WHITE + "End";
                    this.setWidth((int) FontUtil.getStringWidth(renderString));
                    this.setHeight((int) FontUtil.getStringHeight(renderString));
                    drawString(renderString);
                    break;
            }
        }
    }

    public enum DimensionModes {
        Normal,
        Short
    }
}
