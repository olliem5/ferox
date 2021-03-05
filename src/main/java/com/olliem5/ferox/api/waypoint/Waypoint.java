package com.olliem5.ferox.api.waypoint;

import net.minecraft.util.math.BlockPos;

/**
 * @author olliem5
 */

public final class Waypoint {
    private String name;
    private BlockPos blockPos;

    public Waypoint(String name, BlockPos blockPos) {
        this.name = name;
        this.blockPos = blockPos;
    }

    public String getName() {
        return name;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
