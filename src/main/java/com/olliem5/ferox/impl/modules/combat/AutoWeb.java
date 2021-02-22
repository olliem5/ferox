package com.olliem5.ferox.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.client.MessageUtil;
import com.olliem5.ferox.api.util.player.InventoryUtil;
import com.olliem5.ferox.api.util.player.PlayerUtil;
import com.olliem5.ferox.api.util.player.TargetUtil;
import com.olliem5.ferox.api.util.render.draw.RenderUtil;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author olliem5
 *
 * TODO: Fix placing 2 webs, maybe add it as other mode
 */

@FeroxModule(name = "AutoWeb", description = "Places webs at your or a target's feet", category = Category.Combat)
public final class AutoWeb extends Module {
    public static final Setting<TargetModes> targetMode = new Setting<>("Target", "The target to go for when placing webs", TargetModes.Self);
    public static final NumberSetting<Double> targetRange = new NumberSetting<>("Target Range", "The range for a target to be found", 1.0, 4.4, 10.0, 1);

    public static final Setting<Boolean> renderPlace = new Setting<>("Render", "Allows the web placements to be rendered", true);
    public static final Setting<RenderModes> renderMode = new Setting<>(renderPlace, "Render Mode", "The type of box to render", RenderModes.Full);
    public static final NumberSetting<Double> outlineWidth = new NumberSetting<>(renderPlace, "Outline Width", "The width of the outline", 1.0, 2.0, 5.0, 1);
    public static final Setting<Color> renderColour = new Setting<>(renderPlace, "Render Colour", "The colour for the web placements", new Color(15, 60, 231, 201));

    public AutoWeb() {
        this.addSettings(
                targetMode,
                targetRange,
                renderPlace
        );
    }

    private int webSlot;

    private BlockPos renderBlock = null;
    private EntityPlayer target = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        webSlot = InventoryUtil.getHotbarBlockSlot(Blocks.WEB);

        if (webSlot == -1) {
            MessageUtil.sendClientMessage("No Webs, " + ChatFormatting.RED + "Disabling!");
            this.toggle();
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

        if (targetMode.getValue() == TargetModes.Self) {
            target = mc.player;
        } else {
            target = TargetUtil.getClosestPlayer(targetRange.getValue());
        }

        if (target != null) {
            if (!hasWeb(target)) {
                int oldInventorySlot = mc.player.inventory.currentItem;

                if (webSlot != -1) {
                    mc.player.inventory.currentItem = webSlot;
                }

                if (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WEB)) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(new BlockPos(PlayerUtil.getCenter(target.posX, target.posY, target.posZ)), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                }

                renderBlock = new BlockPos(PlayerUtil.getCenter(target.posX, target.posY, target.posZ));

                mc.player.inventory.currentItem = oldInventorySlot;
            }
        }
    }

    private boolean hasWeb(EntityPlayer entityPlayer) {
        return mc.world.getBlockState(entityPlayer.getPosition()).getBlock() == Blocks.WEB;
    }

    @PaceHandler
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        if (renderPlace.getValue() && renderBlock != null) {
            GL11.glLineWidth(outlineWidth.getValue().floatValue());

            RenderUtil.draw(renderBlock, renderMode.getValue() != RenderModes.Outline, renderMode.getValue() != RenderModes.Box, 0, 0, renderColour.getValue());
        }
    }

    public String getArraylistInfo() {
        if (target != null) {
            return target.getName();
        }

        return "";
    }

    public enum TargetModes {
        Self,
        Target
    }

    public enum RenderModes {
        Box,
        Outline,
        Full
    }
}
