package us.ferox.client.impl.gui.click.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.Component;
import us.ferox.client.impl.gui.click.component.ModuleButton;

import java.awt.*;

public class KeybindComponent extends Component {
    private boolean isBinding;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private String points;
    private float tick;

    public KeybindComponent(ModuleButton parent, int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.points = ".";
        this.tick = 0;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 16, new Color(20, 20, 20, 150).getRGB());
        Gui.drawRect(parent.parent.getX() + 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() -1, parent.parent.getY() + offset + 15, new Color(50, 50, 50, 150).getRGB());

        if (isBinding) {
            tick += 0.5f;

            FontUtil.drawText("Listening" + ChatFormatting.GRAY + " " + points, parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
        } else {
            FontUtil.drawText("Bind" + ChatFormatting.GRAY + " " + Keyboard.getKeyName(parent.mod.getKey()), parent.parent.getX() + 4, parent.parent.getY() + offset + 3, -1);
        }

        if (isBinding) {
            if (tick >= 15f) {
                points = "..";
            }
            if (tick >= 30f) {
                points = "...";
            }
            if (tick >= 45f) {
                points = ".";
                tick = 0.0f;
            }
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.isBinding = !this.isBinding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.isBinding) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else {
                this.parent.mod.setKey(key);
                this.isBinding = false;
            }
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
