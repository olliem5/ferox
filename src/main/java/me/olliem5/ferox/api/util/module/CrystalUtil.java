package me.olliem5.ferox.api.util.module;

import me.olliem5.ferox.api.traits.Minecraft;
import me.olliem5.ferox.api.util.minecraft.BlockUtil;
import me.olliem5.ferox.api.util.minecraft.PlayerUtil;
import me.olliem5.ferox.impl.modules.combat.AutoCrystal;
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
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.util.List;
import java.util.stream.Collectors;

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

            if (entity instanceof EntityLivingBase)
                damage = getBlastReduction((EntityLivingBase) entity, calculatedDamage * ((mc.world.getDifficulty().getId() == 0) ? 0.0f : ((mc.world.getDifficulty().getId() == 2) ? 1.0f : ((mc.world.getDifficulty().getId() == 1) ? 0.5f : 1.5f))), new Explosion(mc.world, null, posX, posY, posZ, 6.0f, false, true));

            return (float) damage;
        } catch (Exception e) {}

        return 0;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            damage *= 1.0f - MathHelper.clamp((float) EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), DamageSource.causeExplosionDamage(explosion)), 0.0f, 20.0f) / 25.0f;

            if (entity.isPotionActive(MobEffects.RESISTANCE))
                damage -= damage / 4.0f;

            return damage;
        }

        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    public static void attackCrystal(EntityEnderCrystal entityEnderCrystal, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketUseEntity(entityEnderCrystal));
        } else {
            mc.playerController.attackEntity(mc.player, entityEnderCrystal);
        }
    }

    public static void placeCrystal(BlockPos placePos, EnumFacing enumFacing, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePos, enumFacing, mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, placePos, enumFacing, new Vec3d(0, 0, 0), mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
    }

    public static EnumFacing getEnumFacing(boolean rayTrace, BlockPos placePosition) {
        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(placePosition.getX() + 0.5, placePosition.getY() - 0.5, placePosition.getZ() + 0.5));

        if (placePosition.getY() == 255)
            return EnumFacing.DOWN;

        if (rayTrace) {
            return (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        }

        return EnumFacing.UP;
    }
}
