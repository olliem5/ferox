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

@FeroxComponent(name = "GoldenAppleCount",description = "Displays the number of golden apples you have in your inventory")
public class GappleCountComponent extends Component {
    public static final Setting<CountModes> countMode = new Setting<>("Mode","Mode", CountModes.Normal);

    public GappleCountComponent() {
        this.addSettings(
                countMode
        );
    }

    int gapples;

    @Override
    public void render() {
        String renderString;

        this.gapples = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            this.gapples += mc.player.getHeldItemOffhand().getCount();
        }

        switch (countMode.getValue()) {
            case Normal:
                renderString = "Gapples " + ChatFormatting.WHITE + this.gapples;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "Gap " + ChatFormatting.WHITE + this.gapples;
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
