package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author Manesko
 */


@FeroxComponent(name = "Durability",description = "Shows the durability of the item you have in your hand on screen")
public class DurabilityComponent extends Component {
    int durability;

    Setting<DurabilityModes> durabilitymode = new Setting("Mode","Mode",DurabilityModes.Normal);

    public DurabilityComponent() {
        addSettings(
                durabilitymode
        );
    }


    @Override
    public void render() {
        String DurabilityText;

        int maxdura = mc.player.getHeldItemMainhand().getMaxDamage();
        this.durability = maxdura - mc.player.getHeldItemMainhand().getItemDamage();

        switch ((DurabilityModes) durabilitymode.getValue()) {
            case Normal: {
                DurabilityText = "Durability " + ChatFormatting.WHITE + this.durability;
                drawString(DurabilityText);
                this.setHeight((int) FontUtil.getStringHeight(DurabilityText));
                this.setWidth((int) FontUtil.getStringWidth(DurabilityText));
                break;
            }
            case OnlyNumber: {
                DurabilityText = "" + ChatFormatting.WHITE + this.durability;
                drawString(DurabilityText);
                this.setHeight((int) FontUtil.getStringHeight(DurabilityText));
                this.setWidth((int) FontUtil.getStringWidth(DurabilityText));
                break;
            }
        }

    }

    public enum DurabilityModes {
        Normal,OnlyNumber
    }
}
