package me.olliem5.ferox.impl.modules.movement.speed.modes;

import me.olliem5.ferox.api.util.math.MathUtil;
import me.olliem5.ferox.impl.events.PlayerMoveEvent;
import me.olliem5.ferox.impl.modules.movement.Speed;
import me.olliem5.ferox.impl.modules.movement.speed.SpeedMode;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.MobEffects;
import org.lwjgl.input.Keyboard;

/**
 * @author olliem5
 * @author SrRina
 *
 * Currently this does NOT bypass.
 * TODO: Make it bypass!
 *
 * @since 7/02/21
 */

public final class Strafe extends SpeedMode {
    private double speed;

    private boolean jumpState;

    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiChat || mc.currentScreen != null) return;

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        if (shouldSpeedNotRun()) return;

        double[] playerMovement = MathUtil.transformStrafeMovement(mc.player);

        if (Speed.strafeSprint.getValue()) {
            try {
                if (mc.player.getFoodStats().getFoodLevel() > 6f) {
                    mc.player.setSprinting(true);
                }
            } catch (Exception exception) {}
        }

        speed = (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) > 0.2873 ? Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) + (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) >= 0.34 ? Speed.strafeSpeed.getValue() / 1000 : 0.0) : 0.2873);

        if (mc.player.isPotionActive(MobEffects.SPEED) && Speed.strafeSpeedPotionEffect.getValue()) {
            final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();

            speed *= (1.0 + 0.2 * (amplifier + 1));
        }

        if (playerMovement[2] == 0.0 && playerMovement[3] == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            switch (Speed.strafeMode.getValue()) {
                case OnGround:
                    if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
                        jumpState = true;
                    }
                    break;
                case AutoJump:
                    if (Speed.strafeSprint.getValue()) {
                        try {
                            if (mc.player.getFoodStats().getFoodLevel() > 6f) {
                                mc.player.setSprinting(true);
                            }
                        } catch (Exception exception) {}
                    }

                    if (mc.gameSettings.keyBindJump.pressed == false) {
                        mc.gameSettings.keyBindJump.pressed = true;

                        if (mc.player.onGround) {
                            mc.player.jump();
                        }
                    }

                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        jumpState = true;
                    }
                    break;
            }

            if (jumpState) {
                double jump = 0.40123128;

                if (mc.player.onGround) {
                    if (!Speed.strafeSmoothJump.getValue()) {
                        speed = 0.6174077;
                    }

                    if (mc.player.isPotionActive(MobEffects.JUMP_BOOST) && Speed.strafeJumpPotionEffect.getValue()) {
                        jump += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
                    }

                    event.setY(mc.player.motionY = jump);
                }

                jumpState = false;
            }

            event.setX((playerMovement[2] * speed) * Math.cos(Math.toRadians((playerMovement[0] + 90.0f))) + (playerMovement[3] * speed) * Math.sin(Math.toRadians((playerMovement[0] + 90.0f))));
            event.setZ((playerMovement[2] * speed) * Math.sin(Math.toRadians((playerMovement[0] + 90.0f))) - (playerMovement[3] * speed) * Math.cos(Math.toRadians((playerMovement[0] + 90.0f))));
        }
    }
}
