package me.olliem5.ferox.api.util.module;

import net.minecraft.util.math.BlockPos;

public final class CrystalPosition {
    private BlockPos position;
    private double targetDamage;
    private double selfDamage;

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
