package com.olliem5.ferox.impl.modules.ferox;

import baritone.api.BaritoneAPI;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "Baritone", description = "Controls Ferox's Baritone integration", category = Category.Ferox)
public final class Baritone extends Module {
    public static final Setting<Boolean> path = new Setting<>("Path Colour", "The colour to render Baritone's path with", true);
    public static final Setting<Color> pathColour = new Setting<>(path, "Path Colour", "The colour to render Baritone's path with", new Color(116, 39, 241, 195));

    public static final Setting<Boolean> goal = new Setting<>("Goal Colour", "The colour to render Baritone's goal with", true);
    public static final Setting<Color> goalColour = new Setting<>(goal, "Goal Colour", "The colour to render Baritone's goal with", new Color(47, 212, 62, 195));

    public Baritone() {
        this.addSettings(
                path,
                goal
        );

        this.setEnabled(true);
        this.setDrawn(false);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        this.setEnabled(true);
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (path.getValue()) {
            BaritoneAPI.getSettings().colorCurrentPath.value = pathColour.getValue();
        }

        if (goal.getValue()) {
            BaritoneAPI.getSettings().colorGoalBox.value = goalColour.getValue();
        }
    }
}
