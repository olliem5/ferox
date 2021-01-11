package us.ferox.client.impl.gui.click.component.master;

import net.minecraft.client.gui.Gui;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.colour.RainbowUtil;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.Component;
import us.ferox.client.impl.gui.click.component.ModuleButton;
import us.ferox.client.impl.gui.click.component.sub.SubBooleanComponent;

import java.awt.*;
import java.util.ArrayList;

public class BooleanComponent extends Component {
    private ArrayList<Component> subcomponents;
    private Setting<Boolean> op;
    public ModuleButton parent;
    private boolean open;
    private int offset;
    private int x;
    private int y;

    public BooleanComponent(Setting<Boolean> op, ModuleButton parent, int offset) {
        this.subcomponents = new ArrayList<>();
        this.op = op;
        this.parent = parent;
        this.open = false;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        int opY = offset + 16;

        if (op.hasSubSettings()) {
            for (Setting setting : op.getSubSettings()) {
                if (setting.getValue() instanceof Boolean) {
                    this.subcomponents.add(new SubBooleanComponent(setting, this, opY));
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
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 16, new Color(20, 20, 20, 150).getRGB());

        if (op.getValue() == true) {
            Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, RainbowUtil.getRainbow().getRGB());
        }

        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());

        if (op.hasSubSettings()) {
            FontUtil.drawText(op.getName(), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
            FontUtil.drawText("...", parent.parent.getX() + parent.parent.getWidth() - 12, (parent.parent.getY() + offset + 3), -1);
        } else {
            FontUtil.drawText(op.getName(), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
        }

        if (open && !subcomponents.isEmpty()) {
            for (Component comp : subcomponents) {
                comp.renderComponent();
            }
        }
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
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();

        if (!subcomponents.isEmpty()) {
            for (Component comp : subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.op.setValue(!op.getValue());
        }

        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            setOpen(!open);

            parent.parent.refresh();
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
        if (x > this.x && x < this.x + 100 && y > this.y && y < this.y + 16) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
