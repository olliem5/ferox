package me.olliem5.ferox.impl.events;

import me.olliem5.pace.event.Event;
import net.minecraft.util.EnumHandSide;

/**
 * @author olliem5
 */

public final class TransformSideFirstPersonEvent extends Event {
    private final EnumHandSide enumHandSide;

    public TransformSideFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return enumHandSide;
    }
}
