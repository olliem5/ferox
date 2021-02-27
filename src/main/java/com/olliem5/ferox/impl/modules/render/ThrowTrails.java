package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @author olliem5
 *
 * A bit of this is from Ruhama, but I paid $25 for that piece of shit, and I intend to get my money's worth lol
 */

@FeroxModule(name = "ThrowTrails", description = "Shows where thrown entities are going", category = Category.Render)
public final class ThrowTrails extends Module {
    public static final NumberSetting<Double> stayTime = new NumberSetting<>("Stay Time", "The time it takes for a trail to disappear", 0.0, 10.0, 30.0, 1);

    public static final Setting<Boolean> enderpearls = new Setting<>("Enderpearls", "Draws a trail on enderpearls", true);
    public static final NumberSetting<Double> enderpearlWidth = new NumberSetting<>(enderpearls, "Width", "The width of the enderpearl line", 1.0, 3.0, 5.0, 1);
    public static final Setting<Color> enderpearlColour = new Setting<>(enderpearls, "Enderpearl Colour", "The colour to render enderpearl trails with", new Color(205, 50, 197, 245));

    public ThrowTrails() {
        this.addSettings(
                stayTime,
                enderpearls
        );
    }

    private final HashMap<UUID, List<Vec3d>> poses = new HashMap<>();
    private final HashMap<UUID, Double> time = new HashMap<>();

    public void onUpdate() {
        if (nullCheck()) return;

        Iterator iterator = new HashMap(this.time).entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Double> uuidDoubleEntry = (Map.Entry) iterator.next();

            if (uuidDoubleEntry.getValue() <= 0.0) {
                this.poses.remove(uuidDoubleEntry.getKey());
                this.time.remove(uuidDoubleEntry.getKey());
            } else {
                this.time.replace(uuidDoubleEntry.getKey(), uuidDoubleEntry.getValue() - 0.05);
            }
        }

        iterator = this.mc.world.loadedEntityList.iterator();

        while (true) {
            while (true) {
                Entity entity;

                do {
                    if (!iterator.hasNext()) return;

                    entity = (Entity) iterator.next();
                } while (!(entity instanceof EntityEnderPearl));

                if (!this.poses.containsKey(entity.getUniqueID())) {
                    this.poses.put(entity.getUniqueID(), new ArrayList<>(Collections.singletonList(entity.getPositionVector())));
                    this.time.put(entity.getUniqueID(), stayTime.getValue());
                } else {
                    this.time.replace(entity.getUniqueID(), stayTime.getValue());

                    List<Vec3d> vec3ds = this.poses.get(entity.getUniqueID());
                    vec3ds.add(entity.getPositionVector());
                }
            }
        }
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        RenderUtil.prepareGL();

        Iterator iterator = this.poses.entrySet().iterator();

        while (true) {
            Map.Entry entry;

            do {
                if (!iterator.hasNext()) {
                    RenderUtil.releaseGL();

                    return;
                }

                entry = (Map.Entry) iterator.next();

            } while (((List) entry.getValue()).size() <= 2);

            GL11.glBegin(1);

            GL11.glLineWidth(enderpearlWidth.getValue().floatValue());
            GL11.glColor4f(enderpearlColour.getValue().getRed() / 255.0f, enderpearlColour.getValue().getGreen() / 255.0f, enderpearlColour.getValue().getBlue() / 255.0f, enderpearlColour.getValue().getAlpha() / 255.0f);

            for (int i = 1; i < ((List) entry.getValue()).size(); ++i) {
                GL11.glVertex3d(((Vec3d) ((List) entry.getValue()).get(i)).x - mc.getRenderManager().renderPosX, ((Vec3d) ((List) entry.getValue()).get(i)).y - mc.getRenderManager().renderPosY, ((Vec3d) ((List) entry.getValue()).get(i)).z - mc.getRenderManager().renderPosZ);
                GL11.glVertex3d(((Vec3d) ((List) entry.getValue()).get(i - 1)).x - mc.getRenderManager().renderPosX, ((Vec3d) ((List) entry.getValue()).get(i - 1)).y - mc.getRenderManager().renderPosY, ((Vec3d) ((List) entry.getValue()).get(i - 1)).z - mc.getRenderManager().renderPosZ);
            }

            GL11.glEnd();
        }
    }
}
