package me.olliem5.ferox.impl.gui.screens.editor.component;

import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.impl.gui.Component;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public final class BooleanComponentHud extends Component {
    private Setting<Boolean> op;
    private HudComponentButton parent;
    private int offset;
    private int x;
    private int y;

    public BooleanComponentHud(Setting<Boolean> op, HudComponentButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 16, new Color(20, 20, 20, 150).getRGB());

        if (op.getValue() == true) {
            Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, RainbowUtil.getRainbow().getRGB());
        }

        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());

        FontUtil.drawText(op.getName(), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
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
