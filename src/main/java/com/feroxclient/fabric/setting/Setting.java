package com.feroxclient.fabric.setting;

public class Setting<T> {
    final String name;

    T value;

    public Setting(String name, T value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
