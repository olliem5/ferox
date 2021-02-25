package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.math.MathUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "Coordinates", description = "Shows your coordinates")
public final class CoordinatesComponent extends Component {
    public static final Setting<CoordsModes> coordsmode = new Setting<>("Mode", "The way of displaying the coordinates", CoordsModes.InLine);

    public CoordinatesComponent() {
        this.addSettings(
                coordsmode
        );
    }

    @Override
    public void render() {
        float netherMultiple = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell") ? 8.0f : (int) 0.125f;

        int xPosition = (int) MathUtil.roundAvoid(mc.player.posX, 1);
        int yPosition = (int) MathUtil.roundAvoid(mc.player.posY, 1);
        int zPosition = (int) MathUtil.roundAvoid(mc.player.posZ, 1);

        int netherXPosition = (int) ((int) (MathUtil.roundAvoid(mc.player.posX, 1)) * netherMultiple);
        int netherZPosition = (int) ((int) (MathUtil.roundAvoid(mc.player.posZ, 1)) * netherMultiple);

        String fullLine = "XYZ " + ChatFormatting.WHITE + xPosition + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + yPosition + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + xPosition + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + netherXPosition + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + netherZPosition + ChatFormatting.RESET + "]";

        String lineX = "X " + ChatFormatting.WHITE + xPosition + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + netherXPosition + ChatFormatting.RESET + "]";
        String lineY = "Y " + ChatFormatting.WHITE + yPosition;
        String lineZ = "Z " + ChatFormatting.WHITE + zPosition + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + netherZPosition + ChatFormatting.RESET + "]";

        switch (coordsmode.getValue()) {
            case Separate:
                FontUtil.drawText(lineX, getX(), getY(), Colours.clientColourPicker.getValue().getRGB());
                FontUtil.drawText(lineY, getX(), getY() + FontUtil.getStringHeight(lineX), Colours.clientColourPicker.getValue().getRGB());
                FontUtil.drawText(lineZ, getX(), getY() + FontUtil.getStringHeight(lineX) + FontUtil.getStringHeight(lineY), Colours.clientColourPicker.getValue().getRGB());

                if (xPosition > zPosition) {
                    this.setWidth((int) FontUtil.getStringWidth(lineX) + 10);
                } else if (zPosition > xPosition) {
                    this.setWidth((int) FontUtil.getStringWidth(lineZ) + 10);
                }

                this.setHeight((int) (FontUtil.getStringHeight(lineX) + FontUtil.getStringHeight(lineY) + FontUtil.getStringHeight(lineZ)));
                break;
            case InLine:
                drawString(fullLine);

                this.setWidth((int) FontUtil.getStringWidth(fullLine));
                this.setHeight((int) FontUtil.getStringHeight(fullLine));
                break;
        }
    }

    public enum CoordsModes {
        Separate,
        InLine
    }
}
