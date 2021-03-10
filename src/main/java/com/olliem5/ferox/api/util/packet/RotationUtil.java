package com.olliem5.ferox.api.util.packet;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

/**
 * @author linustouchtips
 * @author 086
 */

public final class RotationUtil implements Minecraft {
    public static void lockYaw(double rotation) {
        if (mc.player.rotationYaw >= rotation) {
            mc.player.rotationYaw = 0.0f;
        }

        if (mc.player.rotationYaw <= rotation) {
            mc.player.rotationYaw = 0.0f;
        }
    }

    public static void lockPitch(double rotation) {
        if (mc.player.rotationPitch >= rotation) {
            mc.player.rotationPitch = 0.0f;
        }

        if (mc.player.rotationPitch <= rotation) {
            mc.player.rotationPitch = 0.0f;
        }
    }

    public static String getFacing() {
        switch (MathHelper.floor(mc.player.rotationYaw + 8.0f / 360.0f + 0.5) & 7) {
            case 0:
            case 1:
                return " [" + ChatFormatting.WHITE + "+Z" + ChatFormatting.RESET + "]";
            case 2:
            case 3:
                return " [" + ChatFormatting.WHITE + "-X" + ChatFormatting.RESET + "]";
            case 4:
            case 5:
                return " [" + ChatFormatting.WHITE + "-Z" + ChatFormatting.RESET + "]";
            case 6:
            case 7:
                return " [" + ChatFormatting.WHITE + "+X" + ChatFormatting.RESET + "]";
        }

        return "Invalid Direction";
    }
}
