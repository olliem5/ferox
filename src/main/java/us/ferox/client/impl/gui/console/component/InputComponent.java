package us.ferox.client.impl.gui.console.component;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import us.ferox.client.api.util.colour.RainbowUtil;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.Component;
import us.ferox.client.impl.gui.console.Console;

import java.awt.*;

public class InputComponent extends Component {
    private Console parent;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isTyping;
    private String typedCharacters;

    public InputComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isTyping = false;
        this.typedCharacters = "";
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(x, y, x + width, y + height, RainbowUtil.getRainbow().getRGB());
        Gui.drawRect(x, y, x + width, y + height, new Color(50, 50, 50, 150).getRGB());

        FontUtil.drawText(typedCharacters, x, y, -1);
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.isTyping) {
            if (key == Keyboard.KEY_RETURN) {
                resetText();
            } else {
                String tempText = "";

                if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    tempText = typedChar + "";
                }

                typedCharacters += tempText;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.isTyping = !this.isTyping;
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return (x > this.x && x < this.x + width && y > this.y && y < this.y + height);
    }

    private void resetText() {
        this.executeCommand(typedCharacters);
        this.typedCharacters = "";
    }

    private void executeCommand(String command) {
        mc.player.sendChatMessage(command);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
