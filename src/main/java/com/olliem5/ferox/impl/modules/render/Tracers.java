package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.render.draw.DrawUtil;
import com.olliem5.ferox.api.util.world.EntityUtil;
import com.olliem5.ferox.impl.modules.ferox.Social;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.Objects;

/**
 * @author olliem5
 */

@FeroxModule(name = "Tracers", description = "Renders lines to entities", category = Category.Render)
public final class Tracers extends Module {
    public static final NumberSetting<Double> lineWidth = new NumberSetting<>("Line Width", "The width of the lines", 1.0, 2.0, 5.0, 1);

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", "Allows Tracers to function on crystals", true);
    public static final Setting<Color> crystalColour = new Setting<>(crystals, "Crystal Colour", "The colour for crystals", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> players = new Setting<>("Players", "Allows Tracers to function on players", true);
    public static final Setting<Color> playerColour = new Setting<>(players, "Player Colour", "The colour for players", new Color(106, 22, 219, 100));

    public static final Setting<Boolean> animals = new Setting<>("Animals", "Allows Tracers to function on animals", true);
    public static final Setting<Color> animalColour = new Setting<>(animals, "Animal Colour", "The colour for animals", new Color(75, 219, 22, 100));

    public static final Setting<Boolean> mobs = new Setting<>("Mobs", "Allows Tracers to function on animals", true);
    public static final Setting<Color> mobColour = new Setting<>(mobs, "Mob Colour", "The colour for animals", new Color(236, 13, 29, 100));

    public Tracers() {
        this.addSettings(
                lineWidth,
                crystals,
                players,
                animals,
                mobs
        );
    }

    public static boolean entityCheck(Entity entity) {
        return entity instanceof EntityEnderCrystal && crystals.getValue() || entity instanceof EntityPlayer && players.getValue() || entity instanceof EntityAnimal && animals.getValue() || entity instanceof EntityMob && mobs.getValue();
    }

    public static Color getTracerColour(Entity entity) {
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

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        mc.world.loadedEntityList.stream()
                .filter(Objects::nonNull)
                .filter(entity -> mc.player != entity)
                .filter(Tracers::entityCheck)
                .forEach(entity -> {
                    Vec3d vec3d = EntityUtil.getInterpolatedPos(entity, event.getPartialTicks()).subtract(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY, mc.getRenderManager().renderPosZ);

                    mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);

                    Vec3d vec3d1 = new Vec3d(0, 0, 1).rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));
                    DrawUtil.drawLine3D((float) vec3d1.x, (float) vec3d1.y + mc.player.getEyeHeight(), (float) vec3d1.z, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z, lineWidth.getValue().floatValue(), getTracerColour(entity));

                    mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                });
    }
}
