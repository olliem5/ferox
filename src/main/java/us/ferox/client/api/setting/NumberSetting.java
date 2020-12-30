package us.ferox.client.api.setting;

public class NumberSetting<T extends Number> extends Setting {
	T min;
	T max;

	public NumberSetting(String name, T min, T value, T max) {
		super(name, value);
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