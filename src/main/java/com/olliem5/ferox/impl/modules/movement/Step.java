package com.olliem5.ferox.impl.modules.movement;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * @author olliem5
 */

@FeroxModule(name = "Step", description = "Allows you to step up blocks", category = Category.Movement)
public final class Step extends Module {
    public static final Setting<StepModes> stepMode = new Setting<>("Mode", "The way of performing step", StepModes.Packet);
    public static final Setting<BlockModes> blockMode = new Setting<>("Block", "How many blocks step goes up", BlockModes.TwoBlock);
    public static final Setting<Boolean> reverse = new Setting<>("Reverse", "Makes step do downwards instead of upwards", false);

    public Step() {
        this.addSettings(
                stepMode,
                blockMode,
                reverse
        );
    }

    private final double[] oneBlockOffset = {0.42, 0.753};
    private final double[] twoBlockOffset = {0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.player.stepHeight = 0.5f;
    }

    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.onGround && mc.player.collidedHorizontally && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            if (!reverse.getValue()) {
                switch (stepMode.getValue()) {
                    case Packet:
                        switch (blockMode.getValue()) {
                            case OneBlock:
                                for (double value : oneBlockOffset) {
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + value, mc.player.posZ, mc.player.onGround));
                                }

                                mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ);
                                break;
                            case TwoBlock:
                                for (double value : twoBlockOffset) {
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + value, mc.player.posZ, mc.player.onGround));
                                }

                                mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0, mc.player.posZ);
                                break;
                        }
                        break;
                    case Vanilla:
                        switch (blockMode.getValue()) {
                            case OneBlock:
                                mc.player.stepHeight = 1.0f;
                                break;
                            case TwoBlock:
                                mc.player.stepHeight = 2.0f;
                                break;
                        }
                        mc.player.jump();
                        break;
                }
            } else {
                mc.player.motionY--;
            }
        }
    }

    public enum StepModes {
        Packet,
        Vanilla
    }

    public enum BlockModes {
        OneBlock,
        TwoBlock
    }
}
