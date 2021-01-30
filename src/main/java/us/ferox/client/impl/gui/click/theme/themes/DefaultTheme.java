package us.ferox.client.impl.gui.click.theme.themes;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.setting.NumberSetting;
import us.ferox.client.api.setting.Setting;
import us.ferox.client.api.util.client.EnumUtil;
import us.ferox.client.api.util.colour.RainbowUtil;
import us.ferox.client.api.util.font.FontUtil;
import us.ferox.client.api.util.math.MathUtil;
import us.ferox.client.api.util.render.GuiUtil;
import us.ferox.client.impl.gui.click.theme.Theme;

/**
 * @author bon
 * @author olliem5
 *
 * @since 11/18/20
 */

public class DefaultTheme extends Theme {
	public static final String name = "Default";

	private static int boost = 0;

	public static final int width = 105;
	public static final int height = 14;

	public DefaultTheme() {
		super(name, width, height);
	}
	
	@Override
	public void drawTitles(String name, int x, int y) {
		Gui.drawRect(x, y, (x + width), y + height, 0xFF2F2F2F);
		Gui.drawRect(x, y + height, (x + width), y + height + 1, RainbowUtil.getRollingRainbow(1));

		FontUtil.drawText(name, (x + ((x + width) - x) / 2 - FontUtil.getStringWidth(name) / 2), y + 3, -1);
	}

	@Override
	public void drawModules(List<Module> modules, int x, int y) {
		boost = 0;

		for (Module m : modules) {
			int color = 0xFF212121;

			if (m.isEnabled()) {
				color = 0xFF2F2F2F;
			}

			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (GuiUtil.ldown) {
					m.toggle();
				}

				if (GuiUtil.rdown) {
					m.setOpened(!m.isOpened());
				}
			}

			Gui.drawRect(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + 1 + (boost * height), color);
			FontUtil.drawText(m.getName(), x + 2, y + height + 4 + (boost * height), -1);
			
			if (m.hasSettings()) {
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}

			if (m.isOpened()) {
				if (m.hasSettings()) {
					drawDropdown(m, x, y + 1);
				}

				boost++;
				renderKeybind(m, GuiUtil.keydown, x, y + 1);
			}

			boost++;
		}
	}
	
	public static void drawDropdown(Module m, int x, int y) {
		for (Setting<?> setting : m.getSettings()) {
			if (!setting.isSubSetting()) {
				boost++;

				if (setting.getValue() instanceof Boolean) {
					Setting<Boolean> b = (Setting<Boolean>) setting;
					renderBoolean(b, x, y);
				}

				if (setting.getValue() instanceof Enum) {
					Setting<Enum> sm = (Setting<Enum>) setting;
					renderEnum(sm, x, y);
				}

				if (setting.getValue() instanceof Integer) {
					NumberSetting<Integer> si = (NumberSetting<Integer>) setting;
					renderInteger(si, x, y);
				}

				if (setting.getValue() instanceof Double) {
					NumberSetting<Double> sd = (NumberSetting<Double>) setting;
					renderDouble(sd, x, y);
				}

				if (setting.getValue() instanceof Float) {
					NumberSetting<Float> sd = (NumberSetting<Float>) setting;
					renderFloat(sd, x, y);
				}
			}

			if (setting.isOpened()) {
				for (Setting<?> subSetting : setting.getSubSettings()) {
					boost++;

					if (subSetting.getValue() instanceof Boolean) {
						Setting<Boolean> b = (Setting<Boolean>) subSetting;
						renderSubBoolean(b, x, y);
					}

					if (subSetting.getValue() instanceof Enum) {
						Setting<Enum> sm = (Setting<Enum>) subSetting;
						renderSubEnum(sm, x, y);
					}

					if (subSetting.getValue() instanceof Integer) {
						NumberSetting<Integer> si = (NumberSetting<Integer>) subSetting;
						renderSubInteger(si, x, y);
					}

					if (subSetting.getValue() instanceof Double) {
						NumberSetting<Double> sd = (NumberSetting<Double>) subSetting;
						renderSubDouble(sd, x, y);
					}

					if (subSetting.getValue() instanceof Float) {
						NumberSetting<Float> sd = (NumberSetting<Float>) subSetting;
						renderSubFloat(sd, x, y);
					}
				}
			}
		}
	}

	private static void renderBoolean(Setting<Boolean> setting, int x, int y) {
		int color = 0xFF212121;

		if (setting.getValue()) {
			color = 0xFF2F2F2F;
		}

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.ldown) {
				setting.setValue(!setting.getValue());
			}

			if (GuiUtil.rdown) {
				setting.setOpened(!setting.isOpened());
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);

		if (setting.hasSubSettings()) {
			FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
		}
	}
	
	private static void renderSubBoolean(Setting<Boolean> subSetting, int x, int y) {
		int color = 0xFF212121;

		if (subSetting.getValue()) {
			color = 0xFF2F2F2F;
		}

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.ldown) {
				subSetting.setValue(!subSetting.getValue());
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
	}
	
	private static void renderEnum(Setting<Enum> setting, int x, int y) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.ldown) {
				EnumUtil.setEnumValue(setting, EnumUtil.getNextEnumValue(setting, false));
			}

			if (GuiUtil.rdown) {
				setting.setOpened(setting.isOpened());
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(setting.getName(), x + 3, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(setting.getValue().toString().toUpperCase(), x + 9 + FontUtil.getStringWidth(setting.getName()), (y + height) + 3 + (boost * height), 0xFF767676);

		if (setting.hasSubSettings()) {
			FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
		}
	}
	
	private static void renderSubEnum(Setting<Enum> subSetting, int x, int y) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.ldown) {
				EnumUtil.setEnumValue(subSetting, EnumUtil.getNextEnumValue(subSetting, false));
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(subSetting.getValue().toString().toUpperCase(), x + 12 + FontUtil.getStringWidth(subSetting.getName()), (y + height) + 3 + (boost * height), 0xFF767676);
	}
	
	private static void renderInteger(NumberSetting<Integer> setting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = ((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				setting.setValue((int) (percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin()));
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(setting.getName(), x + 3, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(setting.getValue()), x + FontUtil.getStringWidth(setting.getName()) + 6, (y + height) + 3 + (boost * height), 0xFF767676);

		if (setting.hasSubSettings()) {
			FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
		}
	}
	
	private static void renderSubInteger(NumberSetting<Integer> subSetting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = ((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin());

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue((int) (percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin()));
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(subSetting.getValue()), x + FontUtil.getStringWidth(subSetting.getName()) + 12, (y + height) + 3 + (boost * height), 0xFF767676);
	}
	
	private static void renderDouble(NumberSetting<Double> setting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				setting.setValue(MathUtil.roundDouble(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(setting.getName(), x + 3, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(setting.getValue()), x + FontUtil.getStringWidth(setting.getName()) + 6, (y + height) + 3 + (boost * height), 0xFF767676);

		if (setting.hasSubSettings()) {
			FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
		}
	}
	
	private static void renderSubDouble(NumberSetting<Double> subSetting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue(MathUtil.roundDouble(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(subSetting.getValue()), x + FontUtil.getStringWidth(subSetting.getName()) + 12, (y + height) + 3 + (boost * height), 0xFF767676);
	}

	private static void renderFloat(NumberSetting<Float> setting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				setting.setValue((float) MathUtil.roundDouble(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(setting.getName(), x + 3, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(setting.getValue()), x + FontUtil.getStringWidth(setting.getName()) + 6, (y + height) + 3 + (boost * height), 0xFF767676);

		if (setting.hasSubSettings()) {
			FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
		}
	}

	private static void renderSubFloat(NumberSetting<Float> subSetting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue((float) MathUtil.roundDouble(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
		FontUtil.drawText(String.valueOf(subSetting.getValue()), x + FontUtil.getStringWidth(subSetting.getName()) + 12, (y + height) + 3 + (boost * height), 0xFF767676);
	}

	public static void renderKeybind(Module m, int key, int x, int y) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.ldown) {
				m.setBinding(!m.isBinding());
			}
		}

		if (m.isBinding() && key != -1 && key != Keyboard.KEY_ESCAPE && key != Keyboard.KEY_DELETE) {
			m.setKey((key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK) ? Keyboard.KEY_NONE : key);
			m.setBinding(false);
		}

		if (m.isBinding() && key == Keyboard.KEY_ESCAPE) {
			m.setBinding(false);
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		if (!m.isBinding()) {
			FontUtil.drawText("Keybind", x + 4, (y + height) + 3 + (boost * height), -1);
			FontUtil.drawText(Keyboard.getKeyName(m.getKey()), x + 4 + FontUtil.getStringWidth("Keybind") + 3, (y + height) + 3 + (boost * height), 0xFF767676);
		} else {
			FontUtil.drawText("Listening" + ChatFormatting.GRAY + "...", x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}
}
