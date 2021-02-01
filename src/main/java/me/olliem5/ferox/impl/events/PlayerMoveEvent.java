package me.olliem5.ferox.impl.events;

import net.minecraft.entity.MoverType;
import me.olliem5.ferox.api.event.Event;

public class PlayerMoveEvent extends Event {
    MoverType type;
    double x;
    double y;
    double z;

    public PlayerMoveEvent(MoverType type, double x, double y, double z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoverType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}