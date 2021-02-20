package com.olliem5.ferox.api.util.world;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * @author olliem5
 */

public final class EntityUtil implements Minecraft {
    public static Vec3d getInterpolatedAmount(Entity entity, Double x, Double y, Double z){
        Vec3d vec3d = new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);

        return vec3d;
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec3d){
        return getInterpolatedAmount(entity, vec3d.x, vec3d.y, vec3d.z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Double ticks){
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedPos(Entity entity, Float ticks){
        Vec3d vec3d = new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks.doubleValue()));

        return vec3d;
    }

    public static Vec3d getInterpolatedRenderPos(Entity entity, Float ticks){
        Vec3d subtract = new Vec3d(mc.getRenderManager().viewerPosX, mc.getRenderManager().viewerPosY, mc.getRenderManager().viewerPosZ);

        return getInterpolatedPos(entity, ticks).subtract(subtract);
    }
}
