package com.olliem5.ferox.api.util.render.draw;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

/**
 * A collection of methods to draw certain things, these are pretty much generic across all clients, maybe I'll rewrite in the future.
 *
 * @author olliem5
 * @author linustouchtips
 * @author 3arthqu4ke
 */

public final class DrawUtil implements Minecraft {
    public static void gradient(int minX, int minY, int maxX, int maxY, int startColor, int endColor, boolean left) {
        if (left) {
            float startA = (startColor >> 24 & 0xFF) / 255.0f;
            float startR = (startColor >> 16 & 0xFF) / 255.0f;
            float startG = (startColor >> 8 & 0xFF) / 255.0f;
            float startB = (startColor & 0xFF) / 255.0f;
            float endA = (endColor >> 24 & 0xFF) / 255.0f;
            float endR = (endColor >> 16 & 0xFF) / 255.0f;
            float endG = (endColor >> 8 & 0xFF) / 255.0f;
            float endB = (endColor & 0xFF) / 255.0f;

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glBegin(GL11.GL_POLYGON);
            GL11.glColor4f(startR, startG, startB, startA);
            GL11.glVertex2f(minX, minY);
            GL11.glVertex2f(minX, maxY);
            GL11.glColor4f(endR, endG, endB, endA);
            GL11.glVertex2f(maxX, maxY);
            GL11.glVertex2f(maxX, minY);
            GL11.glEnd();
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        } else {
            drawGradientRect(minX, minY, maxX, maxY, startColor, endColor);
        }
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        float f = (float) (startColor >> 24 & 255) / 255.0f;
        float f1 = (float) (startColor >> 16 & 255) / 255.0f;
        float f2 = (float) (startColor >> 8 & 255) / 255.0f;
        float f3 = (float) (startColor & 255) / 255.0f;
        float f4 = (float) (endColor >> 24 & 255) / 255.0f;
        float f5 = (float) (endColor >> 16 & 255) / 255.0f;
        float f6 = (float) (endColor >> 8 & 255) / 255.0f;
        float f7 = (float) (endColor & 255) / 255.0f;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, top, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, 0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(right, bottom, 0).color(f5, f6, f7, f4).endVertex();

        tessellator.draw();

        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawLeftGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        float f = (float) (startColor >> 24 & 255) / 255.0f;
        float f1 = (float) (startColor >> 16 & 255) / 255.0f;
        float f2 = (float) (startColor >> 8 & 255) / 255.0f;
        float f3 = (float) (startColor & 255) / 255.0f;
        float f4 = (float) (endColor >> 24 & 255) / 255.0f;
        float f5 = (float) (endColor >> 16 & 255) / 255.0f;
        float f6 = (float) (endColor >> 8 & 255) / 255.0f;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0).color(f4, f5, f6, f4).endVertex();
        bufferbuilder.pos(left, top, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(right, bottom, 0).color(f4, f5, f6, f4).endVertex();

        tessellator.draw();

        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPickerBase(int pickerX, int pickerY, int pickerWidth, int pickerHeight, float red, float green, float blue, float alpha) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glVertex2f(pickerX, pickerY);
        GL11.glVertex2f(pickerX, pickerY + pickerHeight);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glVertex2f(pickerX + pickerWidth, pickerY + pickerHeight);
        GL11.glVertex2f(pickerX + pickerWidth, pickerY);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glVertex2f(pickerX, pickerY);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glVertex2f(pickerX, pickerY + pickerHeight);
        GL11.glVertex2f(pickerX + pickerWidth, pickerY + pickerHeight);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glVertex2f(pickerX + pickerWidth, pickerY);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseY, EntityLivingBase entityLivingBase) {
        RenderManager renderManager = mc.getRenderManager();

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0f);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);

        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();

        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 0.0f);

        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntity(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        renderManager.setRenderShadow(true);

        GlStateManager.popMatrix();

        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void glBillboard(float x, float y, float z) {
        final float scale = 0.02666667f;

        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }

    public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer entityPlayer, float scale) {
        glBillboard(x, y, z);

        final int distance = (int) entityPlayer.getDistance(x, y, z);

        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));

        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }

        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    public static void drawTextCenterBlockPos(BlockPos blockPos, String text) {
        GlStateManager.pushMatrix();

        glBillboardDistanceScaled(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, mc.player, 1.0f);

        GlStateManager.disableDepth();

        GlStateManager.translate(-(FontUtil.getStringWidth(text) / 2.0), 0.0, 0.0);

        FontUtil.drawText(text, 0.0f, 0.0f, -1);

        GlStateManager.popMatrix();
    }

    public static void drawCompleteImage(float posX, float posY, float width, float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
