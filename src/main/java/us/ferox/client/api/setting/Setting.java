package us.ferox.client.api.setting;

import us.ferox.client.impl.gui.click.component.master.BooleanComponent;

import java.util.ArrayList;
import java.util.List;

public class Setting<T> {
	private Setting<?> parent;
	private final String name;
	private T value;
	private boolean isSubSetting;

	private final List<Setting> subs = new ArrayList<>();

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

		if (parent.getValue() instanceof Boolean) {
			Setting<Boolean> booleanSetting = (Setting<Boolean>) parent;
			booleanSetting.addSub(this);
		}

		if (parent.getValue() instanceof Enum) {
			Setting<Enum> enumSetting = (Setting<Enum>) parent;
			enumSetting.addSub(this);
		}

		if (parent.getValue() instanceof Integer) {
			NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) parent;
			integerNumberSetting.addSub(this);
		}

		if (parent.getValue() instanceof Double) {
			NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) parent;
			doubleNumberSetting.addSub(this);
		}

		if (parent.getValue() instanceof Float) {
			NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) parent;
			floatNumberSetting.addSub(this);
		}
	}

	public List<Setting> getSubSettings() {
		return this.subs;
	}

	public boolean hasSubSettings() {
		return this.subs.size() > 0;
	}

	public void addSub(Setting<?> s) {
		this.subs.add(s);
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