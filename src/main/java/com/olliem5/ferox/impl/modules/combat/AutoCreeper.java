package com.olliem5.ferox.impl.modules.combat;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.packet.RotationManager;
import com.olliem5.ferox.api.util.player.TargetUtil;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.ferox.api.util.world.HoleUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

/**
 * @author olliem5
 */

@FeroxModule(name = "AutoCreeper", description = "Automatically places creeper eggs at player's feet, for anarchypvp.pw", category = Category.Combat)
public final class AutoCreeper extends Module {
    public static final Setting<AttackModes> attackMode = new Setting<>("Mode", "The mode for attacking players", AttackModes.Hole);
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range for a target to be found", 1.0, 4.4, 10.0, 1);

    public static final Setting<Boolean> rotate = new Setting<>("Rotate", "Allow for rotations", true);
    public static final Setting<RotationModes> rotateMode = new Setting<>(rotate, "Mode", "The mode to use for rotations", RotationModes.Packet);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the creeper egg placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the creeper egg placements", new Color(102, 216, 231, 201));

    public AutoCreeper() {
        this.addSettings(
                attackMode,
                targetRange,
                rotate,
                renderPlace
        );
    }

    private boolean offhand = false;

    private BlockPos placePosition = null;
    private EntityPlayer target = null;

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        placePosition = null;
        target = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        target = TargetUtil.getClosestPlayer(targetRange.getValue());

        offhand = mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG;

        mc.world.playerEntities.stream()
                .filter(Objects::nonNull)
                .filter(target -> target != mc.player)
                .filter(target -> !target.isDead)
                .filter(target -> target.getHealth() > 0)
                .filter(target -> mc.player.getDistance(target) <= targetRange.getValue())
                .filter(target -> HoleUtil.isPlayerInHole(target) || attackMode.getValue() == AttackModes.Always)
                .forEach(target -> {
                    placePosition = new BlockPos(target.posX, target.posY -1, target.posZ);

                    if (mc.player.getHeldItemMainhand().getItem() == Items.SPAWN_EGG || mc.player.getHeldItemOffhand().getItem() == Items.SPAWN_EGG) {
                        if (rotate.getValue()) {
                            RotationManager.rotateToBlockPos(placePosition, rotateMode.getValue() == RotationModes.Packet);
                        }

                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePosition, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                    }
                });
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        if (renderPlace.getValue() && placePosition != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            RenderUtil.draw(placePosition, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
        }
    }

    @Override
    public String getArraylistInfo() {
        if (target != null) {
            return target.getName();
        }

        return "";
    }

    public enum AttackModes {
        Hole,
        Always
    }

    public enum RotationModes {
        Packet,
        Legit
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}
