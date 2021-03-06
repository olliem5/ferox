package com.olliem5.ferox.impl.components;

import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.util.render.draw.DrawUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Player", description = "Shows a model of your player")
public final class PlayerComponent extends Component {
    public static final NumberSetting<Integer> scale = new NumberSetting<>("Scale", "The scale to render the player at", 1, 30, 100, 0);

    public PlayerComponent() {
        this.setWidth(50);
        this.setHeight(80);

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

        // fixing any color bugs (thanks openGL)
        GlStateManager.color(1, 1, 1 ,1);
        DrawUtil.drawEntityOnScreen(this.getX() + 28, this.getY() + 67, scale.getValue(), this.getY() + 13, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}
