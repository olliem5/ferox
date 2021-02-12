package me.olliem5.ferox.impl.components;

import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.component.FeroxComponent;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.util.render.font.FontUtil;

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

        if (this.isTopLeft()) {
            this.setWidth(75);

            ModuleManager.getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()) * (-1)))
                    .forEach(module -> {

                        FontUtil.drawString(module.getName(), this.getPosX(), this.getPosY() + (10 * boost), -1);

                        boost++;
            });

            this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
        }

        if (this.isTopRight()) {
            this.setWidth(-75);

            ModuleManager.getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()) * (-1)))
                    .forEach(module -> {

                        FontUtil.drawString(module.getName(), this.getPosX() - 2 - FontUtil.getStringWidth(module.getName()), this.getPosY() + (10 * boost), -1);

                        boost++;
            });

            this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
        }

        if (this.isBottomLeft()) {
            this.setWidth(75);

            ModuleManager.getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName())))
                    .forEach(module -> {

                        FontUtil.drawString(module.getName(), this.getPosX(), this.getPosY() + (10 * boost), -1);

                        boost++;
            });

            this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
        }

        if (this.isBottomRight()) {
            this.setWidth(-75);

            ModuleManager.getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName())))
                    .forEach(module -> {

                        FontUtil.drawString(module.getName(), this.getPosX() - 2 - FontUtil.getStringWidth(module.getName()), this.getPosY() + (10 * boost), -1);

                        boost++;
            });

            this.setHeight((mc.fontRenderer.FONT_HEIGHT + 1) * boost);
        }
    }
}
