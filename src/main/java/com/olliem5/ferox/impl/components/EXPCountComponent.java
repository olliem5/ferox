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

@FeroxComponent(name = "EXPCount", description = "Displays the amount of exp you have in you inventory, on screen")
public final class EXPCountComponent extends Component {
    public static final Setting<CountModes> countMode = new Setting<>("Mode", "The way of displaying the exp count", CountModes.Normal);

    public EXPCountComponent() {
        this.addSettings(
                countMode
        );
    }

    private int exp;

    @Override
    public void render() {
        String renderString;

        this.exp = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            this.exp += mc.player.getHeldItemOffhand().getCount();
        }

        switch (countMode.getValue()) {
            case Normal:
                renderString = "EXP Bottles " + ChatFormatting.WHITE + this.exp;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "EXP " + ChatFormatting.WHITE + this.exp;
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
