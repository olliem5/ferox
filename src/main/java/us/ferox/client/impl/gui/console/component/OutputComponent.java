package us.ferox.client.impl.gui.console.component;

import net.minecraft.client.gui.Gui;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.impl.gui.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class OutputComponent extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private int offset;
    private ArrayList<String> outputs;

    public OutputComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.outputs = new ArrayList<>();
    }

    @Override
    public void renderComponent() {
        offset = 0;

        for (String string : outputs) {
            FontUtil.drawText(string, x + 1, y + height - FontUtil.getStringHeight(string) - 1 + offset, -1);
            offset -= FontUtil.getStringHeight(string) + 2;
        }

        //Collections.reverse(outputs);
    }

    public void addOutput(String output) {
        this.outputs.add(output);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOffset() {
        return offset;
    }
}
