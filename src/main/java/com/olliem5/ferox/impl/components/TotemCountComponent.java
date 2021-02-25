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

@FeroxComponent(name = "TotemCount",description = "Shows the number of totems in your inventory on screen")
public class TotemCountComponent extends Component {
    int totCount;

    Setting<CountModes> countmode = new Setting("Mode","Mode",CountModes.Normal);

    public TotemCountComponent() {
        addSettings(
                countmode
        );
    }

    @Override
    public void render() {
        String totText;
        this.totCount = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            this.totCount += mc.player.getHeldItemOffhand().getCount();
        }

        switch ((CountModes) countmode.getValue()) {
            case Normal: {
                totText = "Totems " + ChatFormatting.WHITE + this.totCount;
                drawString(totText);
                this.setHeight((int) FontUtil.getStringHeight(totText));
                this.setWidth((int) FontUtil.getStringWidth(totText));
                break;
            }
            case Short: {
                totText = "Tot " + ChatFormatting.WHITE + this.totCount;
                drawString(totText);
                this.setHeight((int) FontUtil.getStringHeight(totText));
                this.setWidth((int) FontUtil.getStringWidth(totText));
                break;
            }
        }
    }

    private enum CountModes {
        Normal,Short
    }
}
