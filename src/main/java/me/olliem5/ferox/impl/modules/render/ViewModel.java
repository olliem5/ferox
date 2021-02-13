package me.olliem5.ferox.impl.modules.render;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.FeroxModule;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.impl.events.TransformSideFirstPersonEvent;
import me.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

/**
 * @author olliem5
 */

@FeroxModule(name = "ViewModel", description = "Changes the way you look in first person", category = Category.RENDER)
public final class ViewModel extends Module {
    public static final Setting<Boolean> cancelEating = new Setting<>("Cancel Eating", "Cancels the eating animation", true);

    public static final Setting<Boolean> leftHand = new Setting<>("Left Hand", "Changes how the left hand is rendered", true);
    public static final NumberSetting<Double> leftX = new NumberSetting<>(leftHand, "Left X", "Changes the X value of the left hand", -2.0, 0.0, 2.0, 1);
    public static final NumberSetting<Double> leftY = new NumberSetting<>(leftHand, "Left Y", "Changes the Y value of the left hand", -2.0, 0.2, 2.0, 1);
    public static final NumberSetting<Double> leftZ = new NumberSetting<>(leftHand, "Left Z", "Changes the Z value of the left hand", -2.0, -1.2, 2.0, 1);

    public static final Setting<Boolean> rightHand = new Setting<>("Right Hand", "Changes how the right hand is rendered", true);
    public static final NumberSetting<Double> rightX = new NumberSetting<>(rightHand, "Right X", "Changes the X value of the right hand", -2.0, 0.0, 2.0, 1);
    public static final NumberSetting<Double> rightY = new NumberSetting<>(rightHand, "Right Y", "Changes the Y value of the right hand", -2.0, 0.2, 2.0, 1);
    public static final NumberSetting<Double> rightZ = new NumberSetting<>(rightHand, "Right Z", "Changes the Z value of the right hand", -2.0, -1.2, 2.0, 1);

    public ViewModel() {
        this.addSettings(
                cancelEating,
                leftHand,
                rightHand
        );
    }

    @PaceHandler
    public void onTransformSideFirstPerson(TransformSideFirstPersonEvent event) {
        if (nullCheck()) return;

        if (leftHand.getValue() && event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(leftX.getValue(), leftY.getValue(), leftZ.getValue());
        }

        if (rightHand.getValue() && event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightX.getValue(), rightY.getValue(), rightZ.getValue());
        }
    }
}
