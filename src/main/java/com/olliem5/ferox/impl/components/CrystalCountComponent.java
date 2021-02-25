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

@FeroxComponent(name = "CrystalCount",description = "Shows the number of crystals in you inventory")
public class CrystalCountComponent extends Component {
    int crystals;

    Setting<CountModes> countMode = new Setting("Mode","Mode",CountModes.Normal);

    public CrystalCountComponent() {
        addSettings(
                countMode
        );
    }

    @Override
    public void render() {
        String countText;
        // much more complicated but it means you count the item in offhand too
        this.crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystals += mc.player.getHeldItemOffhand().getCount();
        }

        switch ((CountModes) countMode.getValue()) {
            case Normal: {
                countText = "Crystals " + ChatFormatting.WHITE + this.crystals;
                drawString(countText);
                this.setHeight((int) FontUtil.getStringHeight(countText));
                this.setWidth((int) FontUtil.getStringWidth(countText));
                break;
            }
            case Short: {
                countText = "Cry " + ChatFormatting.WHITE + this.crystals;
                drawString(countText);
                this.setHeight((int) FontUtil.getStringHeight(countText));
                this.setWidth((int) FontUtil.getStringWidth(countText));
                break;
            }
        }
    }

    private enum CountModes {
        Normal,Short
    }
}
