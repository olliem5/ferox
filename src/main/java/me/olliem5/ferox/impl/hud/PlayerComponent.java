package me.olliem5.ferox.impl.hud;

import me.olliem5.ferox.api.hud.ComponentInfo;
import me.olliem5.ferox.api.hud.HudComponent;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.util.render.draw.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

@ComponentInfo(name = "Player")
public class PlayerComponent extends HudComponent {
    public static NumberSetting<Integer> scale = new NumberSetting<>("Scale", 1, 30, 100, 0);

    public PlayerComponent() {
        setWidth(50);
        setHeight(80);

        this.addSettings(
                scale
        );
    }

    @Override
    public void render() {
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        RenderHelper.drawEntityOnScreen(this.getPosX() + 28, this.getPosY() + 67, scale.getValue(), this.getPosX() + 40, this.getPosY() + 13, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}
