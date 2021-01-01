package us.ferox.client.impl.modules.combat;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Surround", description = "Surrounds you with blocks to minimize crystal damage", category = Category.COMBAT)
public class Surround extends Module {
    public static Setting<PlaceModes> placeMode = new Setting<>("Place", PlaceModes.Full);
    public static Setting<DisableModes> disableMode = new Setting<>("Disable", DisableModes.Finish);

    public static NumberSetting<Integer> blocksPerTick = new NumberSetting<>("BPT", 1, 1, 10);
    public static Setting<Boolean> centerPlayer = new Setting<>("Center Player", true);

    public static Setting<Boolean> timeout = new Setting<>("Timeout", true);
    public static NumberSetting<Double> timeoutTicks = new NumberSetting<>("Timeout Ticks", 1.0, 15.0, 20.0);

    public static Setting<Boolean> onlyObsidian = new Setting<>("Only Obsidian", true);

    private int blocksPlaced = 0;
    private boolean hasPlaced = false;
    private Vec3d center = Vec3d.ZERO;

    @Override
    public void onEnable() {
        if (centerPlayer.getValue()) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;

            center = getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);

            mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
            mc.player.setPosition(center.x, center.y, center.z);
        }
    }

    @Override
    public void onDisable() {
        blocksPlaced = 0;
        hasPlaced = false;
        center = Vec3d.ZERO;
    }

    public void onUpdate() {
        if (nullCheck()) return;


    }

    private final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, -1, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1)
    ));

    private final List<Vec3d> antiCitySurround = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, 0),
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1),
            new Vec3d(2, 0, 0),
            new Vec3d(-2, 0, 0),
            new Vec3d(0, 0, 2),
            new Vec3d(0, 0, -2),
            new Vec3d(3, 0, 0),
            new Vec3d(-3, 0, 0),
            new Vec3d(0, 0, 3),
            new Vec3d(0, 0, -3)
    ));

    private List<Vec3d> getPlaceType() {
        if (placeMode.getValue() == PlaceModes.Standard) {
            return standardSurround;
        } else if (placeMode.getValue() == PlaceModes.Full) {
            return fullSurround;
        }
        return antiCitySurround;
    }

    public Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }

    public enum PlaceModes {
        Standard,
        Full,
        AntiCity
    }

    public enum DisableModes {
        Finish,
        Jump,
        Never
    }
}
