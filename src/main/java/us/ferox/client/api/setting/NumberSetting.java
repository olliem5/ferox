package us.ferox.client.api.setting;

public final class NumberSetting<T extends Number> extends Setting<T> {
	private final T min;
	private final T max;

	public NumberSetting(String name, T min, T value, T max) {
		super(name, value);
		this.min = min;
		this.max = max;
	}

	public NumberSetting(Setting<?> parent, String name, T min, T value, T max) {
		super(parent, name, value);
		this.min = min;
		this.max = max;
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}
}