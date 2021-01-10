package us.ferox.client.impl.hud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import us.ferox.client.api.hud.ComponentInfo;
import us.ferox.client.api.hud.HudComponent;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.util.render.EntityRenderUtil;

@ComponentInfo(name = "Player")
public class PlayerComponent extends HudComponent {
    public static NumberSetting<Integer> scale = new NumberSetting<>("Scale", 1, 30, 100);

    public PlayerComponent() {
        setWidth(50);
        setHeight(80);

        this.addSetting(scale);
    }

    @Override
    public void render() {
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        EntityRenderUtil.drawEntityOnScreen(this.getPosX() + 28, this.getPosY() + 67, scale.getValue(), this.getPosX() + 40, this.getPosY() + 13, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}
