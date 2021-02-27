package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.util.render.font.FontUtil;

/**
 * @author olliem5
 */

@FeroxComponent(name = "PlayerCount", description = "Shows how many players are connected to the server")
public final class PlayerCountComponent extends Component {
    @Override
    public void render() {
        String renderString = "Players " + ChatFormatting.WHITE + mc.player.connection.getPlayerInfoMap().size();
        drawString(renderString);
        this.setWidth((int) FontUtil.getStringWidth(renderString));
        this.setHeight((int) FontUtil.getStringHeight(renderString));
    }
}
