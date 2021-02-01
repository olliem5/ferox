package me.olliem5.ferox.impl.gui.editor.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.font.FontUtil;
import me.olliem5.ferox.impl.gui.Component;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleComponentHud extends Component {
    private NumberSetting<Double> set;
    private HudComponentButton parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double sliderWidth;

    public DoubleComponentHud(NumberSetting<Double> value, HudComponentButton button, int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 16, new Color(20, 20, 20, 150).getRGB());
        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());

        if (set.getValue() != set.getMin()) {
            Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + (int) sliderWidth - 1, parent.parent.getY() + offset + 15, RainbowUtil.getRainbow().getRGB());
            Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + (int) sliderWidth - 1, parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());
        }

        FontUtil.drawText(set.getName() + ChatFormatting.GRAY + " " + set.getValue(), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();

        double diff = Math.min(100, Math.max(0, mouseX - this.x));
        double min = this.set.getMin();
        double max = this.set.getMax();
        this.sliderWidth = 100 * (this.set.getValue() - min) / (max - min);

        if (this.dragging) {
            if (diff == 0) {
                this.set.setValue(this.set.getValue());
            } else {
                double newValue = roundToPlace(diff / 100 * (max - min) + min, 2);
                this.set.setValue(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x && x < this.x + 100 && y > this.y && y < this.y + 16) {
            return true;
        } else {
            return false;
        }
    }
}
