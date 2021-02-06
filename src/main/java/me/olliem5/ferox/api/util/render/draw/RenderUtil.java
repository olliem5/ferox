package me.olliem5.ferox.api.util.render.draw;

import me.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class RenderUtil implements Minecraft {
    
    public static void prepareGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void releaseGL() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static AxisAlignedBB generateBB(long x, long y, long z) {
        BlockPos blockPos = new BlockPos(x, y, z);

        final AxisAlignedBB bb = new AxisAlignedBB(blockPos.getX() - mc.getRenderManager().viewerPosX, blockPos.getY() - mc.getRenderManager().viewerPosY, blockPos.getZ() - mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - mc.getRenderManager().viewerPosX, blockPos.getY() + (1) - mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);

        return bb;
    }

    public static void draw(AxisAlignedBB axisAlignedBB, boolean box, boolean outline, double boxHeight, double outlineHeight, Color colour) {
        prepareGL();

        if (box) {
            drawFilledBox(axisAlignedBB, boxHeight, colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
        }

        if (outline) {
            drawBoundingBox(axisAlignedBB, outlineHeight, colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
        }

        releaseGL();
    }

    /**
     * Below has been taken and modified from RenderGlobal
     * @see net.minecraft.client.renderer.RenderGlobal
     */

    public static void drawBoundingBox(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        buffer.pos(minX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
    }

    public static void addChainedFilledBoxVertices(BufferBuilder builder, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
    }

    public static void drawFilledBox(AxisAlignedBB aabb, double height, float red, float green, float blue, float alpha) {
        renderFilledBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ, height, red, green, blue, alpha);
    }

    public static void renderFilledBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double height, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

        addChainedFilledBoxVertices(bufferbuilder, minX, minY, minZ, maxX, maxY + height, maxZ, red, green, blue, alpha);

        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB box, double height, float red, float green, float blue, float alpha) {
        renderBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, height, red, green, blue, alpha);
    }

    public static void renderBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double height, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);

        drawBoundingBox(bufferbuilder, minX, minY, minZ, maxX, maxY + height, maxZ, red, green, blue, alpha);

        tessellator.draw();
    }
}
