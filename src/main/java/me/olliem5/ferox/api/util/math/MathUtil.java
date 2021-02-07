package me.olliem5.ferox.api.util.math;

import net.minecraft.client.entity.EntityPlayerSP;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author olliem5
 * @author SrRina
 *
 * @since 7/02/21
 */

public final class MathUtil {
    public static double roundNumber(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    public static double[] transformStrafeMovement(EntityPlayerSP entityPlayerSP) {
        double entityRotationYaw   = entityPlayerSP.rotationYaw;
        double entityRotationPitch = entityPlayerSP.rotationPitch;

        double entityForwardMovement = entityPlayerSP.movementInput.moveForward;
        double entityStrafeMovement  = entityPlayerSP.movementInput.moveStrafe;

        if (entityForwardMovement != 0.0 && entityStrafeMovement != 0.0) {
            if (entityForwardMovement != 0.0) {
                if (entityStrafeMovement > 0.0) {
                    entityRotationYaw += ((entityForwardMovement > 0.0) ? -45 : 45);
                } else if (entityStrafeMovement < 0) {
                    entityRotationYaw += ((entityForwardMovement > 0.0) ? 45 : -45);
                }

                entityStrafeMovement = 0.0;

                if (entityStrafeMovement > 0.0) {
                    entityStrafeMovement = 1.0;
                } else if (entityStrafeMovement < 0.0) {
                    entityStrafeMovement = -1.0;
                }
            }
        }

        return new double[] {
                entityRotationYaw, entityRotationPitch,
                entityForwardMovement, entityStrafeMovement
        };
    }
}
