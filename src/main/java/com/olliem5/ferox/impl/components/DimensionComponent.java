package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 */


@FeroxComponent(name = "Dimension",description = "Shows the dimension you are currently in on screen")
public class DimensionComponent extends Component {

    Setting<DimensionModes> dimensionmode = new Setting("Mode","Mode",DimensionModes.Normal);

    @Override
    public void render() {
        String dimension;
        // this is a very complicated way of doing it but its the only way i know. Ill look into a simpler way. :)

        if (mc.player.dimension == -1) {
            switch ((DimensionModes) dimensionmode.getValue()) {
                case Normal:
                    dimension = "Dimension " + ChatFormatting.WHITE + "Nether";
                    drawString(dimension);
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    break;
                case OnlyDimension:
                    dimension = "" + ChatFormatting.WHITE + "Nether";
                    drawString(dimension);
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    break;
            }
        } else if(mc.player.dimension == 0) {
            switch ((DimensionModes) dimensionmode.getValue()) {
                case Normal: {
                    dimension = "Dimension " + ChatFormatting.WHITE + "OverWorld";
                    drawString(dimension);
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    break;
                }
                case OnlyDimension: {
                    dimension = "" + ChatFormatting.WHITE + "OverWorld";
                    drawString(dimension);
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    break;
                }
            }
        } else if(mc.player.dimension == 1) {
            switch ((DimensionModes) dimensionmode.getValue()) {
                case Normal: {
                    dimension = "Dimension " + ChatFormatting.WHITE + "End";
                    drawString(dimension);
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    break;
                }
                case OnlyDimension: {
                    dimension = "" + ChatFormatting.WHITE + "End";
                    drawString(dimension);
                    this.setWidth((int) FontUtil.getStringWidth(dimension));
                    this.setHeight((int) FontUtil.getStringHeight(dimension));
                    break;
                }
            }
        }
    }

    public enum DimensionModes {
        Normal, OnlyDimension
    }
}
