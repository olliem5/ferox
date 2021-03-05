package com.olliem5.ferox.impl.modules.ferox;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.waypoint.Waypoint;
import com.olliem5.ferox.api.waypoint.WaypointManager;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;

@FeroxModule(name = "Waypoints", description = "Allows Ferox's waypoint system to function", category = Category.Ferox)
public final class Waypoints extends Module {
    @Override
    public void onEnable() {
        if (nullCheck()) return;

        WaypointManager.addWaypoint(new Waypoint("Test Waypoint", mc.player.getPosition()));
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        for (Waypoint waypoint : WaypointManager.getWaypoints()) {
            RenderUtil.draw(waypoint.getBlockPos(), true, true, 255, 255, Color.RED);
        }
    }
}
