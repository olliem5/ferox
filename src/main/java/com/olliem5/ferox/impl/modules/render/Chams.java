package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.chams.ChamsMode;
import com.olliem5.ferox.impl.modules.render.chams.modes.Highlight;
import com.olliem5.ferox.impl.modules.render.chams.modes.Vanilla;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

/**
 * @author olliem5
 *
 * TODO: Fix sheep wool colouring & the weird skeletons with fur
 */

@FeroxModule(name = "Chams", description = "Highlights entities", category = Category.Render)
public final class Chams extends Module {
    public static final Setting<ChamsModes> mode = new Setting<>("Mode", "The mode to use for Chams", ChamsModes.Highlight);

    public static final Setting<Boolean> leftArm = new Setting<>("Left Arm", "Allows Chams to function on your left arm", true);
    public static final Setting<Color> leftArmColour = new Setting<>(leftArm, "Left Hand Colour", "The colour for your left arm", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> rightArm = new Setting<>("Right Arm", "Allows Chams to function on your right arm", true);
    public static final Setting<Color> rightArmColour = new Setting<>(rightArm, "Right Hand Colour", "The colour for your right arm", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows Chams to function on crystals", true);
    public static final Setting<Color> crystalColour = new Setting<>(crystals, "Crystal Colour", "The colour for crystals", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> players = new Setting<>("Players", "Allows Chams to function on players", true);
    public static final Setting<Color> playerColour = new Setting<>(players, "Player Colour", "The colour for players", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> animals = new Setting<>("Animals", "Allows Chams to function on animals", true);
    public static final Setting<Color> animalColour = new Setting<>(animals, "Animal Colour", "The colour for animals", new Color(75, 219, 22, 100));

    public static final Setting<Boolean> mobs = new Setting<>("Mobs", "Allows Chams to function on animals", true);
    public static final Setting<Color> mobColour = new Setting<>(mobs, "Mob Colour", "The colour for animals", new Color(236, 13, 29, 100));

    public Chams() {
        this.addSettings(
                mode,
                leftArm,
                rightArm,
                crystals,
                players,
                animals,
                mobs
        );
    }

    public static ChamsMode chamsMode;

    public void onUpdate() {
        if (nullCheck()) return;

        switch (mode.getValue()) {
            case Highlight:
                chamsMode = new Highlight();
                break;
            case Vanilla:
                chamsMode = new Vanilla();
                break;
        }
    }

    public static Color getChamsColour(Entity entity) {
        if (entity instanceof EntityEnderCrystal) {
            return new Color(crystalColour.getValue().getRed(), crystalColour.getValue().getGreen(), crystalColour.getValue().getBlue(), crystalColour.getValue().getAlpha());
        }

        if (entity instanceof EntityPlayer) {
            if (FriendManager.isFriend(entity.getName())) {
                return new Color(Social.friendColour.getValue().getRed(), Social.friendColour.getValue().getGreen(), Social.friendColour.getValue().getBlue(), Social.friendColour.getValue().getAlpha());
            } else if (EnemyManager.isEnemy(entity.getName())) {
                return new Color(Social.enemyColour.getValue().getRed(), Social.enemyColour.getValue().getGreen(), Social.enemyColour.getValue().getBlue(), Social.enemyColour.getValue().getAlpha());
            } else {
                return new Color(playerColour.getValue().getRed(), playerColour.getValue().getGreen(), playerColour.getValue().getBlue(), playerColour.getValue().getAlpha());
            }
        }

        if (entity instanceof EntityAnimal) {
            return new Color(animalColour.getValue().getRed(), animalColour.getValue().getGreen(), animalColour.getValue().getBlue(), animalColour.getValue().getAlpha());
        }

        if (entity instanceof EntityMob) {
            return new Color(mobColour.getValue().getRed(), mobColour.getValue().getGreen(), mobColour.getValue().getBlue(), mobColour.getValue().getAlpha());
        }

        return new Color(255, 255, 255, 255);
    }

    public static boolean entityCheck(Entity entity) {
        return entity instanceof EntityEnderCrystal && crystals.getValue() || entity instanceof EntityPlayer && players.getValue() || entity instanceof EntityAnimal && animals.getValue() || entity instanceof EntityMob && mobs.getValue();
    }

    public enum ChamsModes {
        Highlight,
        Vanilla
    }
}
