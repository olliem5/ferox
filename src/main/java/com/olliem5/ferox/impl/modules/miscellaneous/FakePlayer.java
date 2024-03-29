package com.olliem5.ferox.impl.modules.miscellaneous;

import com.mojang.authlib.GameProfile;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

/**
 * @author olliem5
 */

@FeroxModule(name = "FakePlayer", description = "Spawns a client side player entity, usually for module testing", category = Category.Miscellaneous)
public final class FakePlayer extends Module {
    public static final Setting<NameModes> nameMode = new Setting<>("Name", "The name of the fake player", NameModes.Ollie);
    public static final NumberSetting<Float> health = new NumberSetting<>("Health", "The health of the fake player", 0.0f, 20.0f, 36.0f, 1);

    public FakePlayer() {
        this.addSettings(
                nameMode,
                health
        );
    }

    private EntityOtherPlayerMP fakePlayer = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        generateFakePlayer();

        if (fakePlayer != null) {
            fakePlayer.setHealth(health.getValue());
            fakePlayer.copyLocationAndAnglesFrom(mc.player);
            fakePlayer.rotationYawHead = mc.player.rotationYawHead;
            mc.world.addEntityToWorld(fakePlayer.entityId, fakePlayer);
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.world.removeEntityFromWorld(fakePlayer.entityId);

        fakePlayer = null;
    }

    private void generateFakePlayer() {
        switch (nameMode.getValue()) {
            case Ollie:
                fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("8deac414-6c37-44fb-82bd-6873efc1b0cf"), "_o_b_a_m_a_"));
                break;
            case Draily:
                fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("d4b0faa7-368d-4656-a60b-f859ba0f7853"), "LittleDraily"));
                break;
            case Linus:
                fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("19b3ee58-d118-4f0f-be5b-e223967f6c4a"), "linustouchtips24"));
                break;
            case Reap:
                fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("86d8f2c5-696e-4dff-94d0-7f58357a63cd"), "CafeDev"));
                break;
            case You:
                fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
                break;
        }
    }

    @Override
    public String getArraylistInfo() {
        return nameMode.getValue().toString() + ", " + health.getValue().toString();
    }

    public enum NameModes {
        Ollie,
        Draily,
        Linus,
        Reap,
        You
    }
}
