package com.olliem5.ferox.api.util.math;

import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

public final class EnumUtil {
    public static String getNextEnumValue(Setting<Enum<?>> setting, boolean reverse) {
        Enum<?> currentValue = setting.getValue();

        int i = 0;

        for (; i < setting.getValue().getClass().getEnumConstants().length; i++) {
            Enum<?> e = setting.getValue().getClass().getEnumConstants()[i];

            if (e.name().equalsIgnoreCase(currentValue.name())) break;
        }

        return setting.getValue().getClass().getEnumConstants()[(reverse ? (i != 0 ? i - 1 : setting.getValue().getClass().getEnumConstants().length - 1) : i + 1) % setting.getValue().getClass().getEnumConstants().length].toString();
    }

    public static void setEnumValue(Setting<Enum<?>> setting, String value) {
        for (Enum<?> e : setting.getValue().getClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(value)) {
                setting.setValue(e);

                break;
            }
        }
    }
}
