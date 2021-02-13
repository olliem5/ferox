package me.olliem5.ferox.impl.modules.movement;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.PacketEvent;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

/**
 * @author olliem5
 */

@FeroxModule(name = "Velocity", description = "Modifies the knockback that you take", category = Category.MOVEMENT)
public final class Velocity extends Module {
    public static final Setting<Boolean> velocity = new Setting<>("Velocity", "Allows for modification of player velocity", true);
    public static final NumberSetting<Float> velocityHorizontal = new NumberSetting<>(velocity, "Horizontal", "Horizontal velocity knockback to take", 0.0f, 0.0f, 100.0f, 1);
    public static final NumberSetting<Float> velocityVeritcal = new NumberSetting<>(velocity, "Vertical", "Vertical velocity knockback to take", 0.0f, 0.0f, 100.0f, 1);

    public static final Setting<Boolean> explosions = new Setting<>("Explosions", "Allows for modification of explosion velocity", true);
    public static final NumberSetting<Float> explosionsHorizontal = new NumberSetting<>(explosions, "Horizontal", "Horizontal explosion knockback to take", 0.0f, 0.0f, 100.0f, 1);
    public static final NumberSetting<Float> explosionsVeritcal = new NumberSetting<>(explosions, "Vertical", "Vertical explosion knockback to take", 0.0f, 0.0f, 100.0f, 1);

    public Velocity() {
        this.addSettings(
                velocity,
                explosions
        );
    }

    @PaceHandler
    public void onPacketRecieve(PacketEvent.Receive event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity) event.getPacket();

            if (sPacketEntityVelocity.getEntityID() == mc.player.entityId) {
                if (velocityHorizontal.getValue() == 0.0f && velocityVeritcal.getValue() == 0.0f) {
                    event.setCancelled(true);
                } else {
                    sPacketEntityVelocity.motionX *= velocityHorizontal.getValue();
                    sPacketEntityVelocity.motionY *= velocityVeritcal.getValue();
                    sPacketEntityVelocity.motionZ *= velocityHorizontal.getValue();
                }
            }
        }

        if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion sPacketExplosion = (SPacketExplosion) event.getPacket();

            if (explosionsHorizontal.getValue() == 0.0f && explosionsVeritcal.getValue() == 0.0f) {
                event.setCancelled(true);
            } else {
                sPacketExplosion.motionX *= velocityHorizontal.getValue();
                sPacketExplosion.motionY *= velocityVeritcal.getValue();
                sPacketExplosion.motionZ *= velocityHorizontal.getValue();
            }
        }
    }
}
