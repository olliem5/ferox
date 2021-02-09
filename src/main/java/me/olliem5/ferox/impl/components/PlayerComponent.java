package me.olliem5.ferox.impl.components;

import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.component.FeroxComponent;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.util.render.draw.DrawUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Player", description = "Shows a model of your player")
public final class PlayerComponent extends Component {
    public static final NumberSetting<Integer> scale = new NumberSetting<>("Scale", "Scale to render the player at", 1, 30, 100, 0);

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

        DrawUtil.drawEntityOnScreen(this.getPosX() + 28, this.getPosY() + 67, scale.getValue(), this.getPosX() + 40, this.getPosY() + 13, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}
