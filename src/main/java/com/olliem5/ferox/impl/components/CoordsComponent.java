package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;

import java.awt.*;

@FeroxComponent(name ="Coords", description = "Shows your coords on screen")
public final class CoordsComponent extends Component {

    public static final Setting<CoordsMode> coordsmode = new Setting<CoordsMode>("Mode","Changes the Mode of the coords", CoordsMode.Line);

    public CoordsComponent() {
        this.addSettings(
                coordsmode
        );
    }

    @Override
    public void render() {

        float netherMultiple = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell") ? 8 : (int) 0.125f;


        int x = (int) mc.player.posX;
        int y = (int) mc.player.posY;
        int z = (int) mc.player.posZ;
        int Nx = (int) ((int) mc.player.posX * netherMultiple);
        int Nz = (int) ((int) mc.player.posZ * netherMultiple);


        String fullLine = "XYZ " + ChatFormatting.WHITE + x + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + y + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + x + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + Nx + ChatFormatting.RESET + ", " + ChatFormatting.WHITE + Nz + ChatFormatting.RESET + "]";

        String lineX = "X " + ChatFormatting.WHITE + x + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + Nx + ChatFormatting.RESET + "]";
        String lineY = "Y " + ChatFormatting.WHITE + y;
        String lineZ = "Z " + ChatFormatting.WHITE + z + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + Nz + ChatFormatting.RESET + "]";


        switch ((CoordsMode) coordsmode.getValue()) {
            case Separate: {
                FontUtil.drawText(lineX,getX(),getY(), Colours.clientColourPicker.getValue().getRGB());
                FontUtil.drawText(lineY,getX(),getY() + 10, Colours.clientColourPicker.getValue().getRGB());
                FontUtil.drawText(lineZ,getX(),getY() + 20, Colours.clientColourPicker.getValue().getRGB());


                if (x > z) {
                    this.setWidth((int) FontUtil.getStringWidth(lineX) + 10);
                } else if (z > x) {
                    this.setWidth((int) FontUtil.getStringWidth(lineZ) + 10);
                }
                this.setHeight(30);
                break;
            }
            case Line: {
                drawString(fullLine);

                this.setHeight((int) FontUtil.getStringHeight(fullLine));
                this.setWidth((int) FontUtil.getStringWidth(fullLine));
                break;
            }
        }
    }



    public enum CoordsMode {
        Separate,Line
    }
}
