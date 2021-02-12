package me.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.client.MessageUtil;
import me.olliem5.ferox.api.util.player.InventoryUtil;
import me.olliem5.ferox.api.util.player.PlayerUtil;
import me.olliem5.ferox.api.util.player.TargetUtil;
import me.olliem5.ferox.api.util.render.draw.RenderUtil;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author olliem5
 */

@FeroxModule(name = "Ignite", description = "Sets players around you on fire", category = Category.COMBAT)
public final class Ignite extends Module {
    public static final Setting<AttackModes> attackMode = new Setting<>("Mode", "The method used to light the enemy on fire", AttackModes.FlintNSteel);
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range for a target to be found", 1.0, 4.4, 10.0, 1);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the placements", new Color(219, 197, 66, 201));

    public Ignite() {
        this.addSettings(
                attackMode,
                targetRange,
                renderPlace
        );
    }

    private int flintNSteelSlot;
    private int lavaBucketSlot;

    private BlockPos renderBlock = null;
    private EntityPlayer target = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        flintNSteelSlot = InventoryUtil.getHotbarItemSlot(Items.FLINT_AND_STEEL);
        lavaBucketSlot = InventoryUtil.getHotbarItemSlot(Items.LAVA_BUCKET);

        switch (attackMode.getValue()) {
            case FlintNSteel:
                if (flintNSteelSlot == -1) {
                    MessageUtil.sendClientMessage("No Flint and Steel, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                break;
            case LavaBucket:
                if (lavaBucketSlot == -1) {
                    MessageUtil.sendClientMessage("No Lava Bucket, " + ChatFormatting.RED + "Disabling!");
                    this.toggle();
                }

                break;
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        renderBlock = null;
        target = null;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        target = TargetUtil.getClosestPlayer(targetRange.getValue());

        if (target != null) {
            final int oldInventorySlot = mc.player.inventory.currentItem;

            switch (attackMode.getValue()) {
                case FlintNSteel:
                    if (flintNSteelSlot != -1) {
                        mc.player.inventory.currentItem = flintNSteelSlot;
                    }

                    if (mc.player.getHeldItemMainhand().getItem() == Items.FLINT_AND_STEEL) {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(new BlockPos(PlayerUtil.getCenter(target.posX, target.posY -1, target.posZ)), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                    }

                    break;
                case LavaBucket:
                    if (lavaBucketSlot != -1) {
                        mc.player.inventory.currentItem = lavaBucketSlot;
                    }

                    if (mc.player.getHeldItemMainhand().getItem() == Items.LAVA_BUCKET) {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(new BlockPos(PlayerUtil.getCenter(target.posX, target.posY -1, target.posZ)), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                    }

                    break;
            }

            renderBlock = new BlockPos(PlayerUtil.getCenter(target.posX, target.posY -1, target.posZ));

            mc.player.inventory.currentItem = oldInventorySlot;
        }
    }

    private boolean isOnFire(EntityPlayer entityPlayer) {
        return mc.world.getBlockState(entityPlayer.getPosition()).getBlock() == Blocks.FIRE;
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        GL11.glLineWidth(outlineWidth.getValue().floatValue());

        if (renderPlace.getValue()) {
            if (renderBlock != null) {
                switch (renderMode.getValue()) {
                    case Box:
                        RenderUtil.draw(renderBlock, true, false, 0, 0, renderColour.getValue());
                        break;
                    case Outline:
                        RenderUtil.draw(renderBlock, false, true, 0, 0, renderColour.getValue());
                        break;
                    case Full:
                        RenderUtil.draw(renderBlock, true, true, 0, 0, renderColour.getValue());
                        break;
                }
            }
        }
    }

    public enum AttackModes {
        FlintNSteel,
        LavaBucket
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}
