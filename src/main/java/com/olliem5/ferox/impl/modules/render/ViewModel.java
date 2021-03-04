package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.events.TransformFirstPersonEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

/**
 * @author olliem5
 */

@FeroxModule(name = "ViewModel", description = "Changes the way you look in first person", category = Category.Render)
public final class ViewModel extends Module {
    public static final Setting<Boolean> cancelEating = new Setting<>("Cancel Eating", "Cancels the eating animation", true);

    public static final Setting<Boolean> leftPosition = new Setting<>("Left Position", "Changes how the left hand is rendered", true);
    public static final NumberSetting<Double> leftX = new NumberSetting<>(leftPosition, "Left X", "Changes the X position of the left hand", -2.0, 0.0, 2.0, 2);
    public static final NumberSetting<Double> leftY = new NumberSetting<>(leftPosition, "Left Y", "Changes the Y position of the left hand", -2.0, 0.2, 2.0, 2);
    public static final NumberSetting<Double> leftZ = new NumberSetting<>(leftPosition, "Left Z", "Changes the Z position of the left hand", -2.0, -1.2, 2.0, 2);

    public static final Setting<Boolean> rightPosition = new Setting<>("Right Position", "Changes how the right hand is rendered", true);
    public static final NumberSetting<Double> rightX = new NumberSetting<>(rightPosition, "Right X", "Changes the X position of the right hand", -2.0, 0.0, 2.0, 2);
    public static final NumberSetting<Double> rightY = new NumberSetting<>(rightPosition, "Right Y", "Changes the Y position of the right hand", -2.0, 0.2, 2.0, 2);
    public static final NumberSetting<Double> rightZ = new NumberSetting<>(rightPosition, "Right Z", "Changes the Z position of the right hand", -2.0, -1.2, 2.0, 2);

    public static final Setting<Boolean> leftRotation = new Setting<>("Left Rotation", "Changes the left hands model rotation", true);
    public static final NumberSetting<Integer> leftYaw = new NumberSetting<>(leftRotation, "Yaw", "Changes yaw of your left hand", -100, 0, 100, 0);
    public static final NumberSetting<Integer> leftPitch = new NumberSetting<>(leftRotation, "Pitch", "Changes pitch of your left hand", -100, 0, 100, 0);
    public static final NumberSetting<Integer> leftRoll = new NumberSetting<>(leftRotation, "Roll", "Changes roll of your left hand", -100, 0, 100, 0);

    public static final Setting<Boolean> rightRotation = new Setting<>("Right Rotation", "Changes the right hands model rotation", true);
    public static final NumberSetting<Integer> rightYaw = new NumberSetting<>(rightRotation, "Yaw", "Changes yaw of your Right hand", -100, 0, 100, 0);
    public static final NumberSetting<Integer> rightPitch = new NumberSetting<>(rightRotation, "Pitch", "Changes pitch of your Right hand", -100, 0, 100, 0);
    public static final NumberSetting<Integer> rightRoll = new NumberSetting<>(rightRotation, "Roll", "Changes roll of your Right hand", -100, 0, 100, 0);

    public ViewModel() {
        this.addSettings(
                cancelEating,
                leftPosition,
                rightPosition,
                leftRotation,
                rightRotation
        );
    }

    @PaceHandler
    public void onTransformSideFirstPerson(TransformFirstPersonEvent.Pre event) {
        if (nullCheck()) return;

        if (leftPosition.getValue() && event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(leftX.getValue(), leftY.getValue(), leftZ.getValue());
        }

        if (rightPosition.getValue() && event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightX.getValue(), rightY.getValue(), rightZ.getValue());
        }
    }

    @PaceHandler
    public void onTransFormPost(TransformFirstPersonEvent.Post event) {
        if (nullCheck()) return;

        if (leftRotation.getValue() && event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.rotate(leftYaw.getValue(),0,1,0);
            GlStateManager.rotate(leftPitch.getValue(),1,0,0);
            GlStateManager.rotate(leftRoll.getValue(),0,0,1);
        }

        if (rightRotation.getValue() && event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.rotate(rightYaw.getValue(),0,1,0);
            GlStateManager.rotate(rightPitch.getValue(),1,0,0);
            GlStateManager.rotate(rightRoll.getValue(),0,0,1);
        }
    }
}
