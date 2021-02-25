package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;

/**
 * @author Manesko
 */

@FeroxComponent(name = "Combat Info",description = "Displays info on combat modules on screen")
public class PvPInfoComponent extends Component {

    Setting<PvPInfoModes> pvpinfomode = new Setting("Mode","Mode",PvPInfoModes.Normal);

    public PvPInfoComponent() {
        addSettings(
                pvpinfomode
        );
    }

    @Override
    public void render() {
        switch ((PvPInfoModes) pvpinfomode.getValue()) {
            case Normal: {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    FontUtil.drawText( "AutoCrystal " + ChatFormatting.GREEN + "ON!",getX(),getY(), Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText("AutoCrystal " + ChatFormatting.RED + "OFF!",getX(),getY(), Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("AutoCreeper").isEnabled()) {
                    FontUtil.drawText( "AutoCreeper " + ChatFormatting.GREEN + "ON!",getX(),getY() + 10, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText("AutoCreeper " + ChatFormatting.RED + "OFF!",getX(),getY() + 10, Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("AntiCrystal").isEnabled()) {
                    FontUtil.drawText( "AntiCrystal " + ChatFormatting.GREEN + "ON!",getX(),getY() + 20, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText("AntiCrystal " + ChatFormatting.RED + "OFF!",getX(),getY() + 20, Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    FontUtil.drawText( "Surround " + ChatFormatting.GREEN + "ON!",getX(),getY() + 30, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText("Surround " + ChatFormatting.RED + "OFF!",getX(),getY() + 30, Colours.clientColourPicker.getValue().getRGB());
                }

                this.setWidth(83);
                this.setHeight(40);
                break;
            }
            case Short: {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    FontUtil.drawText( ChatFormatting.GREEN + "AC",getX(),getY(), Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText(ChatFormatting.RED + "AC",getX(),getY(), Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("AutoCreeper").isEnabled()) {
                    //ACR??? idk what else to put
                    FontUtil.drawText( ChatFormatting.GREEN + "ACR",getX(),getY() + 10, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText(ChatFormatting.RED + "ACR",getX(),getY() + 10, Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("AntiCrystal").isEnabled()) {
                    //again idk what else to put
                    FontUtil.drawText( ChatFormatting.GREEN + "ATC",getX(),getY() + 20, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText(ChatFormatting.RED + "ATC",getX(),getY() + 20, Colours.clientColourPicker.getValue().getRGB());
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    FontUtil.drawText(ChatFormatting.GREEN + "SU",getX(),getY() + 30, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    FontUtil.drawText(ChatFormatting.RED + "SU",getX(),getY() + 30, Colours.clientColourPicker.getValue().getRGB());
                }
                this.setHeight(40);
                this.setWidth(20);
                break;
            }
        }
    }


    public enum PvPInfoModes {
        Normal, Short
    }
}
