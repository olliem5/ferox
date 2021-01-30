package us.ferox.client.impl.modules.movement;

import git.littledraily.eventsystem.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.impl.events.PacketEvent;

/**
 * TODO: NoPush
 */

@ModuleInfo(name = "Velocity", description = "Modifies the knockback that you take", category = Category.MOVEMENT)
public class Velocity extends Module {
    public static Setting<Boolean> velocity = new Setting<>("Velocity", true);
    public static Setting<Boolean> explosions = new Setting<>("Explosions", true);

    public static NumberSetting<Float> horizontal = new NumberSetting<>("Horizontal", 0.0f, 0.0f, 100.0f, 0);
    public static NumberSetting<Float> vertical = new NumberSetting<>("Vertical", 0.0f, 0.0f, 100.0f, 0);

    public Velocity() {
        this.addSetting(velocity);
        this.addSetting(explosions);
        this.addSetting(horizontal);
        this.addSetting(vertical);
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
