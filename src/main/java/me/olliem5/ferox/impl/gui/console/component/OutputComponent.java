package me.olliem5.ferox.impl.gui.console.component;

import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.impl.gui.Component;

import java.util.ArrayList;
import java.util.Collections;

public class OutputComponent extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
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
        int offset = 0;

        Collections.reverse(outputs);

        for (String string : outputs) {
            FontUtil.drawText(string, x + 1, y + height - FontUtil.getStringHeight(string) - 1 + offset, -1);
            offset -= FontUtil.getStringHeight(string) + 2;
        }

        Collections.reverse(outputs);
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
}
