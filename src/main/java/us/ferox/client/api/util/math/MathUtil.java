package us.ferox.client.api.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
    public static double roundDouble(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
