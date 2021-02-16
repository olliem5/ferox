package com.olliem5.ferox.api.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author olliem5
 */

public final class MathUtil {
    public static double roundNumber(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
