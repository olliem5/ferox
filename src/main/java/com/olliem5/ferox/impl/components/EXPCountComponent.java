package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author Manesko
 */

@FeroxComponent(name = "EXPCount", description = "Displays the amount of exp you have in you inventory, on screen")
public class EXPCountComponent extends Component {
    int exp;

    Setting<CountModes> countmode = new Setting("Mode","Mode",CountModes.Normal);

    public EXPCountComponent() {
        addSettings(
                countmode
        );
    }


    @Override
    public void render() {
        String EXPText;

        this.exp = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            this.exp += mc.player.getHeldItemOffhand().getCount();
        }


        switch ((CountModes)countmode.getValue()) {
            case Normal: {
                EXPText = "Bottles " + ChatFormatting.WHITE + this.exp;
                drawString(EXPText);
                this.setHeight((int) FontUtil.getStringHeight(EXPText));
                this.setWidth((int) FontUtil.getStringWidth(EXPText));
                break;
            }
            case Short: {
                EXPText = "EXP " + ChatFormatting.WHITE + this.exp;
                drawString(EXPText);
                this.setHeight((int) FontUtil.getStringHeight(EXPText));
                this.setWidth((int) FontUtil.getStringWidth(EXPText));
                break;
            }
        }
    }

    private enum CountModes {
        Normal, Short
    }
}
