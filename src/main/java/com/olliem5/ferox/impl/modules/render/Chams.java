package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;

import java.awt.*;

/**
 * @author olliem5
 *
 * TODO: Fix sheep wool colouring
 */

@FeroxModule(name = "Chams", description = "Highlights entities", category = Category.Render)
public final class Chams extends Module {
    public static final Setting<Boolean> leftHand = new Setting<>("Left Hand", "Allows chams to function on your left hand", true);
    public static final Setting<Color> leftHandColour = new Setting<>(leftHand, "Left Hand Colour", "The colour for your left hand", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> rightHand = new Setting<>("Right Hand", "Allows chams to function on your right hand", true);
    public static final Setting<Color> rightHandColour = new Setting<>(rightHand, "Right Hand Colour", "The colour for your right hand", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows chams to function on crystals", true);
    public static final Setting<ChamsModes> crystalsMode = new Setting<>(crystals, "Mode", "The mode of chams to use on crystals", ChamsModes.Highlight);
    public static final Setting<Color> crystalColour = new Setting<>(crystals, "Crystal Colour", "The colour for crystals", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> players = new Setting<>("Players", "Allows chams to function on players", true);
    public static final Setting<ChamsModes> playersMode = new Setting<>(players, "Mode", "The mode of chams to use on players", ChamsModes.Highlight);
    public static final Setting<Color> playerColour = new Setting<>(players, "Player Colour", "The colour for players", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> animals = new Setting<>("Animals", "Allows chams to function on animals", true);
    public static final Setting<ChamsModes> animalsMode = new Setting<>(animals, "Mode", "The mode of chams to use on animals", ChamsModes.Highlight);
    public static final Setting<Color> animalColour = new Setting<>(animals, "Animal Colour", "The colour for animals", new Color(75, 219, 22, 100));

    public static final Setting<Boolean> mobs = new Setting<>("Mobs", "Allows chams to function on animals", true);
    public static final Setting<ChamsModes> mobsMode = new Setting<>(mobs, "Mode", "The mode of chams to use on mobs", ChamsModes.Highlight);
    public static final Setting<Color> mobColour = new Setting<>(mobs, "Mob Colour", "The colour for animals", new Color(236, 13, 29, 100));

    public Chams() {
        this.addSettings(
                leftHand,
                rightHand,
                crystals,
                players,
                animals,
                mobs
        );
    }

    public enum ChamsModes {
        Highlight,
        Vanilla
    }
}
