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

@FeroxComponent(name = "TotemCount",description = "Shows the number of totems in your inventory on screen")
public class TotemCountComponent extends Component {
    public static final Setting<CountModes> countmode = new Setting<>("Mode","Mode",CountModes.Normal);

    public TotemCountComponent() {
        this.addSettings(
                countmode
        );
    }

    private int totems;

    @Override
    public void render() {
        String renderString;

        this.totems = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            this.totems += mc.player.getHeldItemOffhand().getCount();
        }

        switch (countmode.getValue()) {
            case Normal:
                renderString = "Totems " + ChatFormatting.WHITE + this.totems;
                this.setWidth((int) FontUtil.getStringWidth(renderString));
                this.setHeight((int) FontUtil.getStringHeight(renderString));
                drawString(renderString);
                break;
            case Short:
                renderString = "Tot " + ChatFormatting.WHITE + this.totems;
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
