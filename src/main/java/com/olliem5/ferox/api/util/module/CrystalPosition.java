package com.olliem5.ferox.api.util.module;

import net.minecraft.util.math.BlockPos;

/**
 * @author olliem5
 */

public final class CrystalPosition {
    private final BlockPos position;
    private final double targetDamage;
    private final double selfDamage;

    public CrystalPosition(BlockPos position, double targetDamage, double selfDamage) {
        this.position = position;
        this.targetDamage = targetDamage;
        this.selfDamage = selfDamage;
    }

    public BlockPos getPosition() {
        return position;
    }

    public double getTargetDamage() {
        return targetDamage;
    }

    public double getSelfDamage() {
        return selfDamage;
    }
}
