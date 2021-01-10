package us.ferox.client.impl.hud;

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
        glStateManager.disableRescaleNormal();
        glStateManager.setActiveTexture(openGlHelper.lightmapTexUnit);
        glStateManager.disableTexture2D();
        glStateManager.setActiveTexture(openGlHelper.defaultTexUnit);

        EntityRenderUtil.drawEntityOnScreen(this.getPosX() + 28, this.getPosY() + 67, scale.getValue(), this.getPosX() + 40, this.getPosY() + 13, mc.player);

        glStateManager.enableRescaleNormal();
        glStateManager.enableTexture2D();
        glStateManager.enableBlend();

        glStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}
