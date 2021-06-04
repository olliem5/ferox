package com.feroxclient.fabric.setting;

public class NumberSetting<T extends Number> extends Setting<T> {
    T min, max;

    public NumberSetting(String name, T defaultValue, T minValue, T maxValue) {
        super(name, defaultValue);
        min = minValue;
        max = maxValue;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }
}
