package com.olliem5.ferox.impl.components;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * @author olliem5
 */

@FeroxComponent(name = "TabGUI", description = "Allows you to easily toggle modules with the arrow keys")
public final class TabGUIComponent extends Component {
    public TabGUIComponent() {
        Ferox.EVENT_BUS.register(this);
    }

    private int selectedCategory = 0;
    private int selectedModule = 0;

    private boolean modulesShowing = false;

    private final Category[] categories = Category.values();
    private Module currentModule = null;

    @Override
    public void render() {
        this.setWidth(60);
        this.setHeight(84);

        if (selectedCategory < 0) {
            selectedCategory = 0;
        }

        if (selectedCategory > categories.length - 1) {
            selectedCategory = categories.length - 1;
        }

        int categoryBoost = 0;

        for (Category category : categories) {
            if (categories[selectedCategory] == category) {
                Gui.drawRect(this.getX(), this.getY() + categoryBoost, this.getX() + 60, this.getY() + categoryBoost + 12, Colours.clientColourPicker.getValue().getRGB());
            } else {
                Gui.drawRect(this.getX(), this.getY() + categoryBoost, this.getX() + 60, this.getY() + categoryBoost + 12, 0xFF2F2F2F);
            }

            FontUtil.drawText(category.toString(), this.getX() + 2, this.getY() + categoryBoost + 2, -1);
            categoryBoost += 12;
        }

        int moduleBoost = 0;

        if (modulesShowing) {
            ArrayList<Module> modules = ModuleManager.getModulesInCategory(categories[selectedCategory]);

            if (selectedModule < 0) {
                selectedModule = 0;
            }

            if (selectedModule > modules.size() - 1) {
                selectedModule = modules.size() - 1;
            }

            int width = 60;

            for (Module module : modules) {
                int newWidth = (int) (FontUtil.getStringWidth(module.getName()) + 2);

                if (newWidth > width){
                    width = newWidth;
                }

                if (modules.get(selectedModule) == module || module.isEnabled()) {
                    Gui.drawRect(this.getX() + 60, this.getY() + moduleBoost, this.getX() + 60 + width, this.getY() + moduleBoost + 12, Colours.clientColourPicker.getValue().getRGB());
                } else {
                    Gui.drawRect(this.getX() + 60, this.getY() + moduleBoost,  this.getX() + 60 + width, this.getY() + moduleBoost + 12, 0xFF2F2F2F);
                }

                FontUtil.drawText(module.getName(), this.getX() + 62, this.getY() + moduleBoost + 2, -1);

                if (modules.get(selectedModule) == module) {
                    currentModule = module;
                }

                moduleBoost += 12;
            }
        }
    }

    @PaceHandler
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            if (!modulesShowing) {
                selectedCategory--;
            } else {
                selectedModule--;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            if (!modulesShowing) {
                selectedCategory++;
            } else {
                selectedModule++;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            modulesShowing = false;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            if (!modulesShowing) {
                modulesShowing = true;
            } else {
                if (currentModule != null) {
                    currentModule.toggle();
                }
            }
        }
    }
}
