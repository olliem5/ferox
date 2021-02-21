package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Arraylist", description = "Shows you what modules you have enabled")
public final class ArraylistComponent extends Component {
    public static final Setting<SortModes> sortMode = new Setting<>("Sort", "The way of sorting the modules", SortModes.Down);

    public ArraylistComponent() {
        this.addSettings(
                sortMode
        );
    }

    private int boost;

    @Override
    public void render() {
        boost = 0;

        int screenWidth = new ScaledResolution(mc).getScaledWidth();

        if (this.getX() < (screenWidth / 2)) {
            renderRight();
        } else {
            renderLeft();
        }

        this.setWidth(130);
        this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
    }

    private void renderLeft() {
        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()) * (sortMode.getValue() == SortModes.Down ? (-1) : 1)))
                .forEach(module -> {
                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getX() + 128 - FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()), this.getY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }

    private void renderRight() {
        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()) * (sortMode.getValue() == SortModes.Down ? (-1) : 1)))
                .forEach(module -> {
                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getX(), this.getY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }

    public enum SortModes {
        Up,
        Down
    }
}
