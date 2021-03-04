package com.olliem5.ferox.impl.events;

import com.olliem5.pace.event.Event;
import net.minecraft.util.EnumHandSide;

/**
 * @author olliem5
 */

public abstract class TransformFirstPersonEvent extends Event {
    private final EnumHandSide enumHandSide;

    public TransformFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return enumHandSide;
    }

    public static class Pre extends TransformFirstPersonEvent {
        public Pre(EnumHandSide enumHandSide) {
            super(enumHandSide);
        }
    }

    public static class Post extends TransformFirstPersonEvent {

        public Post(EnumHandSide enumHandSide) {
            super(enumHandSide);
        }
    }
}
