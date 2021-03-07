package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author olliem5
 * @author IngrosWare devs
 *
 * TODO: Either rewrite this completely, or make it not shit
 */

@FeroxModule(name = "Trajectories", description = "Shows you where throwable items will go", category = Category.Render)
public final class Trajectories extends Module {
    public static final Setting<Boolean> render = new Setting<>("Render", "Allows the trajectory to be rendered", true);
    public static final Setting<Color> renderColour = new Setting<>(render, "Colour", "The colour for the trajectory", new Color(38, 25, 224, 255));

    public Trajectories() {
        this.addSettings(
                render
        );
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        double renderPosX = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks();
        double renderPosY = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks();
        double renderPosZ = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks();

        mc.player.getHeldItem(EnumHand.MAIN_HAND);

        if (mc.gameSettings.thirdPersonView != 0) return;

        else if (!(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFishingRod || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEnderPearl || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEgg || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSnowball || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle)) return;

        Item item = mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();

        double posX = renderPosX - MathHelper.cos(mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        double posY = renderPosY + mc.player.getEyeHeight() - 0.1000000014901161;
        double posZ = renderPosZ - MathHelper.sin(mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        double motionX = -MathHelper.sin(mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(mc.player.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);
        double motionY = -MathHelper.sin(mc.player.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);
        double motionZ = MathHelper.cos(mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(mc.player.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);

        int var6 = 72000 - mc.player.getItemInUseCount();

        float power = var6 / 20.0f;

        power = (power * power + power * 2.0f) / 3.0f;

        if (power > 1.0f) {
            power = 1.0f;
        }

        float distance = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);

        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;

        float pow = (item instanceof ItemBow) ? (power * 2.0f) : ((item instanceof ItemFishingRod) ? 1.25f : ((mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.9f : 1.0f));

        motionX *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));
        motionY *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));
        motionZ *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));

        RenderUtil.prepareGL();

        GlStateManager.color(renderColour.getValue().getRed() / 255.0f, renderColour.getValue().getGreen() / 255.0f, renderColour.getValue().getBlue() / 255.0f, renderColour.getValue().getAlpha() / 255.0f);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        float size = (float) ((item instanceof ItemBow) ? 0.3 : 0.25);

        boolean hasLanded = false;

        Entity landingOnEntity = null;
        RayTraceResult landingPosition = null;

        while (!hasLanded && posY > 0.0) {
            Vec3d present = new Vec3d(posX, posY, posZ);
            Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
            RayTraceResult possibleLandingStrip = mc.world.rayTraceBlocks(present, future, false, true, false);

            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != RayTraceResult.Type.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }

            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
            List<?> entities = this.getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));

            for (Object entity : entities) {
                Entity boundingBox = (Entity) entity;

                if (boundingBox.canBeCollidedWith() && boundingBox != mc.player) {
                    float var7 = 0.3f;

                    AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand(var7, var7, var7);
                    RayTraceResult possibleEntityLanding = var8.calculateIntercept(present, future);

                    if (possibleEntityLanding == null) continue;

                    hasLanded = true;
                    landingOnEntity = boundingBox;
                    landingPosition = possibleEntityLanding;
                }
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;

            float motionAdjustment = 0.99f;

            motionX *= motionAdjustment;
            motionY *= motionAdjustment;
            motionZ *= motionAdjustment;

            motionY -= ((item instanceof ItemBow) ? 0.05 : 0.03);

            drawLine3D(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
        }

        if (landingPosition != null && landingPosition.typeOfHit == RayTraceResult.Type.BLOCK && render.getValue()) {
            GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);

            int side = landingPosition.sideHit.getIndex();

            if (side == 2) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            } else if (side == 3) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            } else if (side == 4) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            } else if (side == 5) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }

            Cylinder c = new Cylinder();

            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);

            c.setDrawStyle(100011);

            if (landingOnEntity != null) {
                GlStateManager.color(renderColour.getValue().getRed() / 255.0f, renderColour.getValue().getGreen() / 255.0f, renderColour.getValue().getBlue() / 255.0f, renderColour.getValue().getAlpha() / 255.0f);

                GL11.glLineWidth(2.5f);

                c.draw(0.6f, 0.3f, 0.0f, 4, 1);

                GL11.glLineWidth(0.1f);

                GlStateManager.color(renderColour.getValue().getRed() / 255.0f, renderColour.getValue().getGreen() / 255.0f, renderColour.getValue().getBlue() / 255.0f, renderColour.getValue().getAlpha() / 255.0f);
            }

            c.draw(0.6f, 0.3f, 0.0f, 4, 1);
        }

        RenderUtil.releaseGL();
    }

    public void drawLine3D(double var1, double var2, double var3) {
        GL11.glVertex3d(var1, var2, var3);
    }

    private List<?> getEntitiesWithinAABB(AxisAlignedBB axisAlignedBB) {
        ArrayList<Entity> list = new ArrayList<>();

        int chunkMinX = MathHelper.floor((axisAlignedBB.minX - 2.0) / 16.0);
        int chunkMaxX = MathHelper.floor((axisAlignedBB.maxX + 2.0) / 16.0);
        int chunkMinZ = MathHelper.floor((axisAlignedBB.minZ - 2.0) / 16.0);
        int chunkMaxZ = MathHelper.floor((axisAlignedBB.maxZ + 2.0) / 16.0);

        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (mc.world.getChunkProvider().getLoadedChunk(x, z) != null) {
                    mc.world.getChunk(x, z).getEntitiesWithinAABBForEntity(mc.player, axisAlignedBB, list, null);
                }
            }
        }

        return list;
    }
}
