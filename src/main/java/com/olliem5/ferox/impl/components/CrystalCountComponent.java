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
 * @author olliem5
 */

@FeroxComponent(name = "CrystalCount", description = "Shows the number of crystals in your inventory")
public final class CrystalCountComponent extends Component {
    public static final Setting<CountModes> countMode = new Setting<>("Mode", "The way of displaying the crystal count", CountModes.Normal);

    public CrystalCountComponent() {
        this.addSettings(
                countMode
        );
    }

    private int crystals;

    @Override
    public void render() {
        String renderString;

        this.crystals = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystals += mc.player.getHeldItemOffhand().getCount();
        }

        switch (countMode.getValue()) {
            case Normal:
                renderString = "Crystals " + ChatFormatting.WHITE + this.crystals;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "Cry " + ChatFormatting.WHITE + this.crystals;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
        }
    }

    private enum CountModes {
        Normal,
        Short
    }
}
