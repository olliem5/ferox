package us.ferox.client.impl.gui.click.component.sub;

import net.minecraft.client.gui.Gui;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.colour.RainbowUtil;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.Component;
import us.ferox.client.impl.gui.click.component.master.BooleanComponent;

import java.awt.*;

public class SubBooleanComponent extends Component {
    private Setting<Boolean> op;
    private BooleanComponent parent;
    private int offset;
    private int x;
    private int y;

    public SubBooleanComponent(Setting<Boolean> op, BooleanComponent parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.parent.getX() + parent.parent.parent.getWidth();
        this.y = parent.parent.parent.getY() + parent.parent.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.parent.getX(), parent.parent.parent.getY() + offset, parent.parent.parent.getX() + parent.parent.parent.getWidth(), parent.parent.parent.getY() + offset + 16, new Color(20, 20, 20, 150).getRGB());

        if (op.getValue() == true) {
            Gui.drawRect(parent.parent.parent.getX() + 1, parent.parent.parent.getY() + offset, parent.parent.parent.getX() + parent.parent.parent.getWidth() -1, parent.parent.parent.getY() + offset + 15, RainbowUtil.getRainbow().getRGB());
        }

        Gui.drawRect(parent.parent.parent.getX() + 1, parent.parent.parent.getY() + offset, parent.parent.parent.getX() + parent.parent.parent.getWidth() -1, parent.parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());

        FontUtil.drawText(op.getName(), parent.parent.parent.getX() + 4, parent.parent.parent.getY() + offset + 3, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.parent.getY() + this.offset;
        this.x = parent.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.parent.isOpen()) {
            this.op.setValue(!op.getValue());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 100 && y > this.y && y < this.y + 16) {
            return true;
        } else {
            return false;
        }
    }
}
