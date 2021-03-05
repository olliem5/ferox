package com.olliem5.ferox.impl.modules.render.esp;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * @author olliem5
 */

public abstract class ESPMode implements Minecraft {
    public void drawESP(RenderWorldLastEvent event) {}
}
