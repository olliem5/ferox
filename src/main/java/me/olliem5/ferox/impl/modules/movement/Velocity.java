package me.olliem5.ferox.impl.modules.movement;

import git.littledraily.eventsystem.Listener;
import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.PacketEvent;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

/**
 * TODO: NoPush
 */

@FeroxModule(name = "Velocity", description = "Modifies the knockback that you take", category = Category.MOVEMENT)
public final class Velocity extends Module {
    public static final Setting<Boolean> velocity = new Setting<>("Velocity", "Modifies player velocity", true);
    public static final Setting<Boolean> explosions = new Setting<>("Explosions", "Modifies explosion velocity", true);

    public static final NumberSetting<Float> horizontal = new NumberSetting<>("Horizontal", "Horizontal knockback to take", 0.0f, 0.0f, 100.0f, 1);
    public static final NumberSetting<Float> vertical = new NumberSetting<>("Vertical", "Vertical knockback to take", 0.0f, 0.0f, 100.0f, 1);

    public Velocity() {
        this.addSettings(
                velocity,
                explosions,
                horizontal,
                vertical
        );
    }

    @Listener
    public void onPacketRecieve(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity) event.getPacket();

            if (sPacketEntityVelocity.getEntityID() == mc.player.entityId) {
                if (horizontal.getValue() == 0.0f && vertical.getValue() == 0.0f) {
                    event.cancel();

                    sPacketEntityVelocity.motionX *= horizontal.getValue();
                    sPacketEntityVelocity.motionY *= vertical.getValue();
                    sPacketEntityVelocity.motionZ *= horizontal.getValue();
                }
            }
        } else if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion sPacketExplosion = (SPacketExplosion) event.getPacket();

            if (horizontal.getValue() == 0.0f && vertical.getValue() == 0.0f) {
                event.cancel();

                sPacketExplosion.motionX *= horizontal.getValue();
                sPacketExplosion.motionY *= vertical.getValue();
                sPacketExplosion.motionZ *= horizontal.getValue();
            }
        }
    }
}
