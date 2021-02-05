package me.olliem5.ferox.api.setting;

import java.util.ArrayList;

public class Setting<T> {
	private Setting<?> parent;
	private final String name;
	private final String description;

	private T value;

	private boolean isSubSetting;
	private boolean isOpened;

	private final ArrayList<Setting> subSettings = new ArrayList<>();

	public Setting(String name, String description, T value) {
		this.name = name;
		this.description = description;
		this.value = value;

		this.isSubSetting = false;
	}

	public Setting(Setting<?> parent, String name, String description, T value) {
		this.parent = parent;
		this.name = name;
		this.description = description;
		this.value = value;

		this.isSubSetting = true;

		if (parent.getValue() instanceof Boolean) {
			Setting<Boolean> booleanSetting = (Setting<Boolean>) parent;
			booleanSetting.addSubSetting(this);
		}

		if (parent.getValue() instanceof Enum) {
			Setting<Enum> enumSetting = (Setting<Enum>) parent;
			enumSetting.addSubSetting(this);
		}

		if (parent.getValue() instanceof Integer) {
			NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) parent;
			integerNumberSetting.addSubSetting(this);
		}

		if (parent.getValue() instanceof Double) {
			NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) parent;
			doubleNumberSetting.addSubSetting(this);
		}

		if (parent.getValue() instanceof Float) {
			NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) parent;
			floatNumberSetting.addSubSetting(this);
		}
	}

	public ArrayList<Setting> getSubSettings() {
		return this.subSettings;
	}

	public boolean hasSubSettings() {
		return this.subSettings.size() > 0;
	}

	public void addSubSetting(Setting<?> subSetting) {
		this.subSettings.add(subSetting);
	}

	public Setting<?> getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public T getValue() {
		return value;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isSubSetting() {
		return isSubSetting;
	}

	public void setOpened(boolean opened) {
		isOpened = opened;
	}
}