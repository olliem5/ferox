package us.ferox.client.api.setting;

public class Setting<T> {
	private Setting<?> parent;
	private final String name;
	private T value;
	private boolean isSubSetting;

	public Setting(String name, T value) {
		this.name = name;
		this.value = value;

		isSubSetting = false;
	}

	public Setting(Setting<?> parent, String name, T value) {
		this.parent = parent;
		this.name = name;
		this.value = value;

		isSubSetting = true;
	}

	public Setting<?> getParent() {
		return parent;
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

	public boolean isSubSetting() {
		return isSubSetting;
	}
}