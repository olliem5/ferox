package us.ferox.client.api.traits;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;

import static us.ferox.client.api.traits.Minecraft.mc;

public interface Render {
    GlStateManager glStateManager = new GlStateManager();
    OpenGlHelper openGlHelper = new OpenGlHelper();
    RenderHelper renderHelper = new RenderHelper();
    RenderManager renderManager = mc.getRenderManager();
}
