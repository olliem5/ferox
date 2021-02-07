package me.olliem5.ferox.api.util.module;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.minecraft.BlockUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

/**
 * @author linustouchtips
 * @since 11/24/2020
 */

public final class CrystalUtil implements Minecraft {
    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        try {
            double factor = (1.0 - entity.getDistance(posX, posY, posZ) / 12.0f) * entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());

            float calculatedDamage = (float) (int) ((factor * factor + factor) / 2.0 * 7.0 * 12.0f + 1.0);

            double damage = 1.0;

            if (entity instanceof EntityLivingBase) {
                damage = getBlastReduction((EntityLivingBase) entity, calculatedDamage * ((mc.world.getDifficulty().getId() == 0) ? 0.0f : ((mc.world.getDifficulty().getId() == 2) ? 1.0f : ((mc.world.getDifficulty().getId() == 1) ? 0.5f : 1.5f))), new Explosion(mc.world, null, posX, posY, posZ, 6.0f, false, true));
            }

            return (float) damage;
        } catch (Exception e) {}

        return 0;
    }

    public static float getBlastReduction(EntityLivingBase entityLivingBase, float damage, Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entityLivingBase.getTotalArmorValue(), (float) entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            damage *= 1.0f - MathHelper.clamp((float) EnchantmentHelper.getEnchantmentModifierDamage(entityLivingBase.getArmorInventoryList(), DamageSource.causeExplosionDamage(explosion)), 0.0f, 20.0f) / 25.0f;

            if (entityLivingBase.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }

            return damage;
        }

        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entityLivingBase.getTotalArmorValue(), (float) entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

        return damage;
    }

    public static void breakCrystal(EntityEnderCrystal entityEnderCrystal, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketUseEntity(entityEnderCrystal));
        } else {
            mc.playerController.attackEntity(mc.player, entityEnderCrystal);
        }
    }

    public static void placeCrystal(BlockPos blockPos, EnumFacing enumFacing, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos, enumFacing, new Vec3d(0, 0, 0), mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
    }

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean multiPlace, boolean thirteen) {
        try {
            if (BlockUtil.getBlockResistance(blockPos) != BlockUtil.BlockResistance.Unbreakable && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }

            if (!thirteen && BlockUtil.getBlockResistance(blockPos.add(0, 2, 0)) != BlockUtil.BlockResistance.Blank || BlockUtil.getBlockResistance(blockPos.add(0, 1, 0)) != BlockUtil.BlockResistance.Blank) {
                return false;
            }

            for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos.add(0, 1, 0)))) {
                if (entity.isDead || multiPlace && entity instanceof EntityEnderCrystal) continue;

                return false;
            }

            if (!thirteen) {
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos.add(0, 2, 0)))) {
                    if (entity.isDead || multiPlace && entity instanceof EntityEnderCrystal) continue;

                    return false;
                }
            }
        } catch (Exception exception) {
            return false;
        }

        return true;
    }

    public static EnumFacing getEnumFacing(boolean rayTrace, BlockPos blockPos) {
        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(blockPos.getX() + 0.5, blockPos.getY() - 0.5, blockPos.getZ() + 0.5));

        if (blockPos.getY() == 255) {
            return EnumFacing.DOWN;
        }

        if (rayTrace) {
            return (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        }

        return EnumFacing.UP;
    }
}
