package us.ferox.client.api.util.render;

import net.minecraft.entity.EntityLivingBase;
import us.ferox.client.api.traits.Minecraft;
import us.ferox.client.api.traits.Render;

public class EntityRenderUtil implements Minecraft, Render {
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase entityLivingBase) {
        glStateManager.enableColorMaterial();
        glStateManager.pushMatrix();
        glStateManager.translate((float) posX, (float) posY, 50.0F);
        glStateManager.scale((float) (-scale), (float) scale, (float) scale);
        glStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        glStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        renderHelper.enableStandardItemLighting();
        glStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        glStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        glStateManager.translate(0.0F, 0.0F, 0.0F);
        renderManager.setPlayerViewY(180.0F);
        renderManager.setRenderShadow(false);
        renderManager.renderEntity(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        renderManager.setRenderShadow(true);
        glStateManager.popMatrix();
        renderHelper.disableStandardItemLighting();
        glStateManager.disableRescaleNormal();
        glStateManager.setActiveTexture(openGlHelper.lightmapTexUnit);
        glStateManager.disableTexture2D();
        glStateManager.setActiveTexture(openGlHelper.defaultTexUnit);
    }
}
