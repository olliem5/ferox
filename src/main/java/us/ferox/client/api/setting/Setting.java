package us.ferox.client.api.setting;

public class Setting<T> {
	private String name;
	T value;

	public Setting(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}