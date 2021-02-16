package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;

import java.util.Comparator;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Arraylist", description = "Shows you what modules you have enabled")
public final class ArraylistComponent extends Component {
    private int boost;

    @Override
    public void render() {
        boost = 0;

        switch (this.getScreenPosition()) {
            case TopLeft:
                renderTopLeft();
                break;
            case BottomLeft:
                renderBottomLeft();
                break;
            case TopRight:
                renderTopRight();
                break;
            case BottomRight:
                renderBottomRight();
                break;
        }

        this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
    }

    private void renderTopLeft() {
        this.setWidth(75);

        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()) * (-1)))
                .forEach(module -> {

                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getPosX(), this.getPosY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }

    private void renderBottomLeft() {
        this.setWidth(75);

        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo())))
                .forEach(module -> {

                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getPosX(), this.getPosY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }

    private void renderTopRight() {
        this.setWidth(-75);

        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()) * (-1)))
                .forEach(module -> {

                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getPosX() - 2 - FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()), this.getPosY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }

    private void renderBottomRight() {
        this.setWidth(-75);

        ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo())))
                .forEach(module -> {

                    FontUtil.drawText(module.getName() + " " + ChatFormatting.WHITE + module.getArraylistInfo(), this.getPosX() - 2 - FontUtil.getStringWidth(module.getName() + " " + module.getArraylistInfo()), this.getPosY() + (10 * boost), Colours.clientColourPicker.getValue().getRGB());

                    boost++;
                });
    }
}
