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

@FeroxComponent(name = "GoldenAppleCount",description = "Displays the number of golden apples you have in your inventory")

public class GoldenAppleCountComponent extends Component {
    int GCount;

    Setting<CountModes> countmode = new Setting("Mode","Mode",CountModes.Normal);

    public GoldenAppleCountComponent() {
        addSettings(
                countmode
        );
    }

    @Override
    public void render() {
        String gapText;
        this.GCount = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            this.GCount += mc.player.getHeldItemOffhand().getCount();
        }

        switch ((CountModes) countmode.getValue()) {
            case Normal: {
                gapText = "GoldenApples " + ChatFormatting.WHITE + this.GCount;
                drawString(gapText);
                this.setHeight((int) FontUtil.getStringHeight(gapText));
                this.setWidth((int) FontUtil.getStringWidth(gapText));
                break;
            }
            case Short: {
                gapText = "Gap " + ChatFormatting.WHITE + this.GCount;
                drawString(gapText);
                this.setHeight((int) FontUtil.getStringHeight(gapText));
                this.setWidth((int) FontUtil.getStringWidth(gapText));
                break;
            }
        }
    }

    private enum CountModes {
        Normal, Short
    }
}
