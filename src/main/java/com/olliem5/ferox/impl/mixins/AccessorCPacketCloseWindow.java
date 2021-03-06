package com.olliem5.ferox.impl.mixins;

import net.minecraft.network.play.client.CPacketCloseWindow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Gav06
 */

// accessing window id field in this packet to make xcarry work
@Mixin(CPacketCloseWindow.class)
public interface AccessorCPacketCloseWindow {

    @Accessor("windowId")
    public int getWindowId();
}
