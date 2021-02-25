package com.olliem5.ferox.impl.components;

import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * @author olliem5
 */

@FeroxComponent(name = "Logo", description = "Renders the client logo image")
public final class LogoComponent extends Component {
    public static final Setting<LogoModes> logoMode = new Setting<>("Mode", "The type of logo to draw", LogoModes.Full);

    public LogoComponent() {
        this.addSettings(
                logoMode
        );
    }

    private final ResourceLocation fullLogo = new ResourceLocation("ferox", "images/logo_new.png");
    private final ResourceLocation rocketLogo = new ResourceLocation("ferox", "images/info_icon.png");
    private final ResourceLocation textLogo = new ResourceLocation("ferox", "images/info_icon.png");

    @Override
    public void render() {
        switch (logoMode.getValue()) {
            case Full:
                mc.getTextureManager().bindTexture(fullLogo);
                Gui.drawModalRectWithCustomSizedTexture(this.getX(), this.getY(), 0, 0, 50, 50, 50, 50);
                this.setWidth(50);
                this.setHeight(50);
                break;
            case Rocket:
                break;
            case Text:
                break;
        }
    }

    public enum LogoModes {
        Full,
        Rocket,
        Text
    }
}
