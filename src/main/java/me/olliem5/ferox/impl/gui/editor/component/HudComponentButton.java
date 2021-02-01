package me.olliem5.ferox.impl.gui.editor.component;

import net.minecraft.client.gui.Gui;
import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.font.FontUtil;
import me.olliem5.ferox.impl.gui.Component;

import java.awt.*;
import java.util.ArrayList;

public class HudComponentButton extends Component {
    private ArrayList<Component> subcomponents;
    public HudComponent mod;
    public HudPanel parent;
    public int offset;
    private boolean open;
    private boolean hovered;

    public HudComponentButton(HudComponent mod, HudPanel parent, int offset) {
        this.subcomponents = new ArrayList<>();
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.open = false;
        this.hovered = false;
        int opY = offset + 16;

        if (mod.getSettings() != null) {
            for (Setting setting : mod.getSettings()) {
                if (setting.getValue() instanceof Boolean) {
                    this.subcomponents.add(new BooleanComponentHud(setting, this, opY));
                }

                if (setting.getValue() instanceof Enum) {
                    this.subcomponents.add(new EnumComponentHud(setting, this, opY));
                }

                if (setting instanceof NumberSetting) {
                    NumberSetting numberSetting = (NumberSetting) setting;

                    if (numberSetting.getValue() instanceof Integer) {
                        this.subcomponents.add(new IntegerComponentHud(numberSetting, this, opY));
                    }

                    if (numberSetting.getValue() instanceof Double) {
                        this.subcomponents.add(new DoubleComponentHud(numberSetting, this, opY));
                    }

                    if (numberSetting.getValue() instanceof Float) {
                        this.subcomponents.add(new FloatComponentHud(numberSetting, this, opY));
                    }
                }
            }
        }
    }

    @Override
    public void setOff(final int newOff) {
        offset = newOff;

        int opY = this.offset + 16;

        for (Component component : this.subcomponents) {
            component.setOff(opY);
            opY += 16;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 16 + offset, new Color(20, 20, 20, 150).getRGB());

        if (mod.isVisible()) {
            Gui.drawRect(parent.getX() + 1, parent.getY() + offset, parent.getX() + parent.getWidth() -1, parent.getY() + 16 + offset -1, RainbowUtil.getRainbow().getRGB());
        }

        Gui.drawRect(parent.getX() + 1, parent.getY() + offset, parent.getX() + parent.getWidth() -1, parent.getY() + 16 + offset -1, new Color(50, 50, 50, 150).getRGB());

        if (hovered == true) {
            FontUtil.drawText(mod.getName(), parent.getX() + 4, parent.getY() + offset + 3, -1);

            if (subcomponents.size() > 1) {
                FontUtil.drawText("...", parent.getX() + parent.getWidth() - 12, (parent.getY() + offset + 3), -1);
            }

        } else {
            FontUtil.drawText(mod.getName(), parent.getX() + 3, parent.getY() + offset + 3, -1);

            if (subcomponents.size() > 1) {
                FontUtil.drawText("...", parent.getX() + parent.getWidth() - 11, (parent.getY() + offset + 3), -1);
            }
        }

        if (open && !subcomponents.isEmpty()) {
            for (Component comp : subcomponents) {
                comp.renderComponent();
            }
        }
    }

    @Override
    public void closeAllSub() {
        this.open = false;
    }

    @Override
    public int getHeight() {
        if (open) {
            return 16 * (subcomponents.size() + 1);
        }

        return 16;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        hovered = isMouseOnButton(mouseX, mouseY);

        if (!subcomponents.isEmpty()) {
            for (Component comp : subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            mod.setVisible(!mod.isVisible());
        }

        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (!isOpen()) {
                parent.closeAllSetting();
                setOpen(true);
            } else {
                setOpen(false);
            }
            parent.refresh();
        }

        for (Component comp : subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return (x > parent.getX() && x < parent.getX() + 100 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 16 + this.offset);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
