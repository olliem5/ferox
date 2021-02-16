package com.olliem5.ferox.api.setting;

/**
 * @author olliem5
 */

public final class NumberSetting<T extends Number> extends Setting<T> {
	private final T min;
	private final T max;

	private final int scale;

	public NumberSetting(String name, String description, T min, T value, T max, int scale) {
		super(name, description, value);

		this.min = min;
		this.max = max;
		this.scale = scale;
	}

	public NumberSetting(Setting<?> parent, String name, String description, T min, T value, T max, int scale) {
		super(parent, name, description, value);

		this.min = min;
		this.max = max;
		this.scale = scale;
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

	public int getScale() {
		return scale;
	}
}