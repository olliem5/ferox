package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.ferox.impl.modules.render.esp.ESPMode;
import com.olliem5.ferox.impl.modules.render.esp.modes.Box;
import com.olliem5.ferox.impl.modules.render.esp.modes.CSGO;
import com.olliem5.ferox.impl.modules.render.esp.modes.Glow;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

/**
 * @author olliem5
 */

@FeroxModule(name = "ESP", description = "Highlights entities in your world", category = Category.Render)
public final class ESP extends Module {
    public static final Setting<ESPModes> mode = new Setting<>("Mode", "The mode to use for ESP", ESPModes.CSGO);
    public static final NumberSetting<Double> lineWidth = new NumberSetting<>("Line Width", "The width of the lines", 1.0, 2.0, 5.0, 1);

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows esp to function on crystals", true);
    public static final Setting<Color> crystalColour = new Setting<>(crystals, "Crystal Colour", "The colour for crystals", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> players = new Setting<>("Players", "Allows esp to function on players", true);
    public static final Setting<Color> playerColour = new Setting<>(players, "Player Colour", "The colour for players", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> animals = new Setting<>("Animals", "Allows esp to function on animals", true);
    public static final Setting<Color> animalColour = new Setting<>(animals, "Animal Colour", "The colour for animals", new Color(75, 219, 22, 100));

    public static final Setting<Boolean> mobs = new Setting<>("Mobs", "Allows esp to function on animals", true);
    public static final Setting<Color> mobColour = new Setting<>(mobs, "Mob Colour", "The colour for animals", new Color(236, 13, 29, 100));

    public ESP() {
        this.addSettings(
                mode,
                lineWidth,
                crystals,
                players,
                animals,
                mobs
        );
    }

    private ESPMode espMode;

    public void onUpdate() {
        if (nullCheck()) return;

        switch (mode.getValue()) {
            case CSGO:
                espMode = new CSGO();
                break;
            case Box:
                espMode = new Box();
                break;
            case Glow:
                espMode = new Glow();
                break;
        }

        if (!espMode.equals(new Glow())) {
            mc.world.loadedEntityList.stream()
                    .filter(Objects::nonNull)
                    .filter(entity -> mc.player != entity)
                    .filter(ESP::entityCheck)
                    .forEach(entity -> entity.setGlowing(false));
        }
    }

    public static boolean entityCheck(Entity entity) {
        return entity instanceof EntityEnderCrystal && ESP.crystals.getValue() || entity instanceof EntityPlayer && ESP.players.getValue() || entity instanceof EntityAnimal && ESP.animals.getValue() || entity instanceof EntityMob && ESP.mobs.getValue();
    }

    public static Color getESPColour(Entity entity) {
        if (entity instanceof EntityEnderCrystal) {
            return new Color(ESP.crystalColour.getValue().getRed(), ESP.crystalColour.getValue().getGreen(), ESP.crystalColour.getValue().getBlue(), ESP.crystalColour.getValue().getAlpha());
        }

        if (entity instanceof EntityPlayer) {
            if (FriendManager.isFriend(entity.getName())) {
                return new Color(Social.friendColour.getValue().getRed(), Social.friendColour.getValue().getGreen(), Social.friendColour.getValue().getBlue(), Social.friendColour.getValue().getAlpha());
            } else if (EnemyManager.isEnemy(entity.getName())) {
                return new Color(Social.enemyColour.getValue().getRed(), Social.enemyColour.getValue().getGreen(), Social.enemyColour.getValue().getBlue(), Social.enemyColour.getValue().getAlpha());
            } else {
                return new Color(ESP.playerColour.getValue().getRed(), ESP.playerColour.getValue().getGreen(), ESP.playerColour.getValue().getBlue(), ESP.playerColour.getValue().getAlpha());
            }
        }

        if (entity instanceof EntityAnimal) {
            return new Color(ESP.animalColour.getValue().getRed(), ESP.animalColour.getValue().getGreen(), ESP.animalColour.getValue().getBlue(), ESP.animalColour.getValue().getAlpha());
        }

        if (entity instanceof EntityMob) {
            return new Color(ESP.mobColour.getValue().getRed(), ESP.mobColour.getValue().getGreen(), ESP.mobColour.getValue().getBlue(), ESP.mobColour.getValue().getAlpha());
        }

        return new Color(255, 255, 255, 255);
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        espMode.drawESP(event);
    }

    public enum ESPModes {
        CSGO,
        Box,
        Glow
    }
}
