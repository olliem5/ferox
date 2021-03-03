package com.olliem5.ferox.impl.events;

import com.olliem5.pace.event.Event;
import net.minecraft.util.EnumHandSide;

public class TransFormFirstPersonPost extends Event {

    private final EnumHandSide handSide;

    public TransFormFirstPersonPost(EnumHandSide handSide){
        this.handSide = handSide;
    }

    public EnumHandSide getHandSide(){
        return handSide;
    }

}
