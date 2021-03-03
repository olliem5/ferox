package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.events.TransFormFirstPersonPost;
import com.olliem5.ferox.impl.events.TransformSideFirstPersonEvent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

/**
 * @author olliem5
 */

@FeroxModule(name = "ViewModel", description = "Changes the way you look in first person", category = Category.Render)
public final class ViewModel extends Module {
    public static final Setting<Boolean> cancelEating = new Setting<>("Cancel Eating", "Cancels the eating animation", true);

    public static final Setting<Boolean> leftHand = new Setting<>("Left Hand", "Changes how the left hand is rendered", true);
    public static final NumberSetting<Double> leftX = new NumberSetting<>(leftHand, "Left X", "Changes the X value of the left hand", -2.0, 0.0, 2.0, 2);
    public static final NumberSetting<Double> leftY = new NumberSetting<>(leftHand, "Left Y", "Changes the Y value of the left hand", -2.0, 0.2, 2.0, 2);
    public static final NumberSetting<Double> leftZ = new NumberSetting<>(leftHand, "Left Z", "Changes the Z value of the left hand", -2.0, -1.2, 2.0, 2);

    public static final Setting<Boolean> rightHand = new Setting<>("Right Hand", "Changes how the right hand is rendered", true);
    public static final NumberSetting<Double> rightX = new NumberSetting<>(rightHand, "Right X", "Changes the X value of the right hand", -2.0, 0.0, 2.0, 2);
    public static final NumberSetting<Double> rightY = new NumberSetting<>(rightHand, "Right Y", "Changes the Y value of the right hand", -2.0, 0.2, 2.0, 2);
    public static final NumberSetting<Double> rightZ = new NumberSetting<>(rightHand, "Right Z", "Changes the Z value of the right hand", -2.0, -1.2, 2.0, 2);

    public static final Setting<Boolean> leftRotate = new Setting<>("Left Hand", "Change the left hands model rotation", true);
    public static final NumberSetting<Integer> leftRotateYaw = new NumberSetting<>(leftRotate, "LeftYaw", "Change yaw of your left hand", -100, 0, 100, 1 );
    public static final NumberSetting<Integer> leftRotatePitch = new NumberSetting<>(leftRotate, "LeftPitch", "Change pitch of your left hand", -100, 0, 100, 1 );
    public static final NumberSetting<Integer> leftRotateRoll = new NumberSetting<>(leftRotate, "LeftRoll", "Change roll of your left hand", -100, 0, 100, 1 );

    public static final Setting<Boolean> rightRotate = new Setting<>("Right Hand", "Change the right hands model rotation", true);
    public static final NumberSetting<Integer> rightRotateYaw = new NumberSetting<>(rightRotate, "RightYaw", "Change yaw of your Right hand", -100, 0, 100, 1 );
    public static final NumberSetting<Integer> rightRotatePitch = new NumberSetting<>(rightRotate, "RightPitch", "Change pitch of your Right hand", -100, 0, 100, 1 );
    public static final NumberSetting<Integer> rightRotateRoll = new NumberSetting<>(rightRotate, "RightRoll", "Change roll of your Right hand", -100, 0, 100, 1 );

    public ViewModel() {
        this.addSettings(
                cancelEating,
                leftHand,
                rightHand,
                leftRotate,
                rightRotate
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

    @PaceHandler
    public void onTransFormPost(TransFormFirstPersonPost event) {

        if (event.getHandSide() == EnumHandSide.LEFT){
            GlStateManager.rotate(leftRotatePitch.getValue(),1,0,0);
            GlStateManager.rotate(leftRotateYaw.getValue(),0,1,0);
            GlStateManager.rotate(leftRotateRoll.getValue(),0,0,1);
        }
        if (event.getHandSide() == EnumHandSide.RIGHT){
            GlStateManager.rotate(rightRotatePitch.getValue(),1,0,0);
            GlStateManager.rotate(rightRotateYaw.getValue(),0,1,0);
            GlStateManager.rotate(rightRotateRoll.getValue(),0,0,1);
        }
    }
}
