package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "Social", description = "Allows for usage of Ferox's social system", category = Category.Ferox)
public final class Social extends Module {
    public static final Setting<Boolean> friends = new Setting<>("Friends", "Allows for usage of the friends system", true);
    public static final Setting<Color> friendColour = new Setting<>(friends, "Friend Colour", "The colour to render friends with", new Color(0, 255, 0, 100));

    public static final Setting<Boolean> enemies = new Setting<>("Enemies", "Allows for usage of the enemy system", true);
    public static final Setting<Color> enemyColour = new Setting<>(enemies, "Enemy Colour", "The colour to render enemies with", new Color(255, 0, 0, 100));

    public Social() {
        this.addSettings(
                friends,
                enemies
        );

        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        this.setEnabled(true);
    }

    public String getArraylistInfo() {
       if (friends.getValue() && enemies.getValue()) {
           return "Friends, Enemies";
       } else if (friends.getValue() && !enemies.getValue()) {
           return "Friends";
       } else if (!friends.getValue() && enemies.getValue()) {
           return "Enemies";
       }

       return "";
    }
}
