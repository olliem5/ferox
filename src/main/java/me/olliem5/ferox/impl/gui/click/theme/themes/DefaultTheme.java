package me.olliem5.ferox.impl.gui.click.theme.themes;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.setting.NumberSetting;
import me.olliem5.ferox.api.setting.Setting;
import me.olliem5.ferox.api.util.math.EnumUtil;
import me.olliem5.ferox.api.util.colour.ColourUtil;
import me.olliem5.ferox.api.util.colour.RainbowUtil;
import me.olliem5.ferox.api.util.render.font.FontUtil;
import me.olliem5.ferox.api.util.math.MathUtil;
import me.olliem5.ferox.api.util.render.draw.RenderHelper;
import me.olliem5.ferox.api.util.render.gui.GuiUtil;
import me.olliem5.ferox.impl.gui.click.theme.Theme;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

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

	public static Color finalColor;
	public static float finalAlpha = 0.2f;

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
	public void drawModules(List<Module> modules, int x, int y, int mouseX, int mouseY) {
		boost = 0;

		for (Module module : modules) {
			int color = 0xFF212121;

			if (module.isEnabled()) {
				color = 0xFF2F2F2F;
			}

			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (GuiUtil.ldown) {
					module.toggle();
				}

				if (GuiUtil.rdown) {
					module.setOpened(!module.isOpened());
				}
			}

			Gui.drawRect(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + 1 + (boost * height), color);

			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(module.getName(), x + 3, y + height + 4 + (boost * height), -1);

				if (module.hasSettings()) {
					FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
				}
			} else {
				FontUtil.drawText(module.getName(), x + 2, y + height + 4 + (boost * height), -1);

				if (module.hasSettings()) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}

			if (module.isOpened()) {
				if (module.hasSettings()) {
					drawDropdown(module, x, y + 1, mouseX, mouseY);
				}

				boost++;

				renderKeybind(module, GuiUtil.keydown, x, y + 1);
			}

			boost++;
		}
	}
	
	public static void drawDropdown(Module module, int x, int y, int mouseX, int mouseY) {
		for (Setting<?> setting : module.getSettings()) {
			if (!setting.isSubSetting()) {
				boost++;

				if (setting.getValue() instanceof Boolean) {
					Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
					renderBoolean(booleanSetting, x, y);
				}

				if (setting.getValue() instanceof Enum) {
					Setting<Enum> enumSetting = (Setting<Enum>) setting;
					renderEnum(enumSetting, x, y);
				}

				if (setting.getValue() instanceof Color) {
					Setting<Color> colorSetting = (Setting<Color>) setting;
					drawColorPicker(colorSetting, x, y, mouseX, mouseY);

					boost += 8;
				}

				if (setting.getValue() instanceof Integer) {
					NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) setting;
					renderInteger(integerNumberSetting, x, y);
				}

				if (setting.getValue() instanceof Double) {
					NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) setting;
					renderDouble(doubleNumberSetting, x, y);
				}

				if (setting.getValue() instanceof Float) {
					NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) setting;
					renderFloat(floatNumberSetting, x, y);
				}
			}

			if (setting.isOpened()) {
				for (Setting<?> subSetting : setting.getSubSettings()) {
					boost++;

					if (subSetting.getValue() instanceof Boolean) {
						Setting<Boolean> booleanSubSetting = (Setting<Boolean>) subSetting;
						renderSubBoolean(booleanSubSetting, x, y);
					}

					if (subSetting.getValue() instanceof Enum) {
						Setting<Enum> enumSubSetting = (Setting<Enum>) subSetting;
						renderSubEnum(enumSubSetting, x, y);
					}

					if (subSetting.getValue() instanceof Color) {
						Setting<Color> colorSubSetting = (Setting<Color>) subSetting;
						drawColorPicker(colorSubSetting, x, y, mouseX, mouseY);

						boost += 8;
					}

					if (subSetting.getValue() instanceof Integer) {
						NumberSetting<Integer> integerSubSetting = (NumberSetting<Integer>) subSetting;
						renderSubInteger(integerSubSetting, x, y);
					}

					if (subSetting.getValue() instanceof Double) {
						NumberSetting<Double> doubleSubSetting = (NumberSetting<Double>) subSetting;
						renderSubDouble(doubleSubSetting, x, y);
					}

					if (subSetting.getValue() instanceof Float) {
						NumberSetting<Float> floatSubSetting = (NumberSetting<Float>) subSetting;
						renderSubFloat(floatSubSetting, x, y);
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

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(setting.getName(), x + 5, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
			} else {
				FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}
		} else {
			FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);
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

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue().toString().toUpperCase(), x + 5, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue().toString().toUpperCase(), x + 4, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue().toString().toUpperCase(), x + 4, (y + height) + 3 + (boost * height), -1);
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

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
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
				setting.setValue(MathUtil.roundNumber(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubDouble(NumberSetting<Double> subSetting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue(MathUtil.roundNumber(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
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
				setting.setValue((float) MathUtil.roundNumber(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
				FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubFloat(NumberSetting<Float> subSetting, int x, int y) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.lheld) {
				int percentError = (GuiUtil.mX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue((float) MathUtil.roundNumber(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
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

	public static void drawPicker(Setting<Color> subColor, int mouseX, int mouseY, int pickerX, int pickerY, int hueSliderX, int hueSliderY, int alphaSliderX, int alphaSliderY) {
		float[] color = new float[] {
				subColor.getValue().RGBtoHSB(subColor.getValue().getRed(), subColor.getValue().getGreen(), subColor.getValue().getBlue(), null)[0],
				subColor.getValue().RGBtoHSB(subColor.getValue().getRed(), subColor.getValue().getGreen(), subColor.getValue().getBlue(), null)[1],
				subColor.getValue().RGBtoHSB(subColor.getValue().getRed(), subColor.getValue().getGreen(), subColor.getValue().getBlue(), null)[2]
		};

		boolean pickingColor = false;
		boolean pickingHue = false;
		boolean pickingAlpha = false;

		int pickerWidth = 100;
		int pickerHeight = 100;

		int hueSliderWidth = 100;
		int hueSliderHeight = 10;

		int alphaSliderWidth = 100;
		int alphaSliderHeight = 10;

		if (GuiUtil.lheld && GuiUtil.mouseOver(pickerX, pickerY, pickerX + pickerWidth, pickerY + pickerHeight)) {
			pickingColor = true;
		}

		if (GuiUtil.lheld && GuiUtil.mouseOver(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + hueSliderHeight)) {
			pickingHue = true;
		}

		if (GuiUtil.lheld && GuiUtil.mouseOver(alphaSliderX, alphaSliderY, alphaSliderX + alphaSliderWidth, alphaSliderY + alphaSliderHeight)) {
			pickingAlpha = true;
		}

		if (!GuiUtil.lheld) {
			pickingColor = pickingHue = pickingAlpha = false;
		}

		if (pickingHue) {
			if (hueSliderWidth > hueSliderHeight) {
				float restrictedX = (float) Math.min(Math.max(hueSliderX, mouseX), hueSliderX + hueSliderWidth);
				color[0] = (restrictedX - (float) hueSliderX) / hueSliderWidth;
			} else {
				float restrictedY = (float) Math.min(Math.max(hueSliderY, mouseY), hueSliderY + hueSliderHeight);
				color[0] = (restrictedY - (float) hueSliderY) / hueSliderHeight;
			}
		}

		if (pickingAlpha) {
			if (alphaSliderWidth > alphaSliderHeight) {
				float restrictedX = (float) Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + alphaSliderWidth);
				finalAlpha = 1 - (restrictedX - (float) alphaSliderX) / alphaSliderWidth;
			} else {
				float restrictedY = (float) Math.min(Math.max(alphaSliderY, mouseY), alphaSliderY + alphaSliderHeight);
				finalAlpha = 1 - (restrictedY - (float) alphaSliderY) / alphaSliderHeight;
			}
		}

		if (pickingColor) {
			float restrictedX = (float) Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth);
			float restrictedY = (float) Math.min(Math.max(pickerY, mouseY), pickerY + pickerHeight);

			color[1] = (restrictedX - (float) pickerX) / pickerWidth;
			color[2] = 1 - (restrictedY - (float) pickerY) / pickerHeight;
		}

		Gui.drawRect(pickerX - 3, pickerY - 2, pickerX + pickerWidth + 2, pickerY + pickerHeight + 30, RainbowUtil.getRollingRainbow(boost));
		Gui.drawRect(pickerX - 2, pickerY - 2, pickerX + pickerWidth + 2, pickerY + pickerHeight + 30, 0xFF212121);

		int selectedColor = Color.HSBtoRGB(color[0], 1.0f, 1.0f);

		float selectedRed = (selectedColor >> 16 & 0xFF) / 255.0f;
		float selectedGreen = (selectedColor >> 8 & 0xFF) / 255.0f;
		float selectedBlue = (selectedColor & 0xFF) / 255.0f;

		RenderHelper.drawPickerBase(pickerX, pickerY, pickerWidth, pickerHeight, selectedRed, selectedGreen, selectedBlue, finalAlpha);

		drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight, color[0]);

		int cursorX = (int) (pickerX + color[1] * pickerWidth);
		int cursorY = (int) ((pickerY + pickerHeight) - color[2] * pickerHeight);

		Gui.drawRect(cursorX - 2, cursorY - 2, cursorX + 2, cursorY + 2, -1);

		drawAlphaSlider(alphaSliderX, alphaSliderY, alphaSliderWidth, alphaSliderHeight, selectedRed, selectedGreen, selectedBlue, finalAlpha);

		finalColor = ColourUtil.integrateAlpha(new Color(Color.HSBtoRGB(color[0], color[1], color[2])), finalAlpha);
	}

	public static void drawHueSlider(int x, int y, int width, int height, float hue) {
		int step = 0;

		if (height > width) {
			Gui.drawRect(x, y, x + width, y + 4, 0xFFFF0000);

			y += 4;

			for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
				int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
				int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);

				RenderHelper.drawGradientRect(x, y + step * (height / 6), x + width, y + (step + 1) * (height / 6), previousStep, nextStep);

				step++;
			}

			int sliderMinY = (int) (y + (height * hue)) - 4;

			Gui.drawRect(x, sliderMinY - 1, x + width, sliderMinY + 1, -1);
		} else {
			for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
				int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
				int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);

				RenderHelper.gradient(x + step * (width / 6), y, x + (step + 1) * (width / 6), y + height, previousStep, nextStep, true);

				step++;
			}

			int sliderMinX = (int) (x + (width * hue));

			Gui.drawRect(sliderMinX - 1, y, sliderMinX + 1, y + height, -1);
		}
	}

	public static void drawAlphaSlider(int x, int y, int width, int height, float red, float green, float blue, float alpha) {
		boolean left = true;

		int checkerBoardSquareSize = height / 2;

		for (int squareIndex = -checkerBoardSquareSize; squareIndex < width; squareIndex += checkerBoardSquareSize) {
			if (!left) {
				Gui.drawRect(x + squareIndex, y, x + squareIndex + checkerBoardSquareSize, y + height, 0xFFFFFFFF);
				Gui.drawRect(x + squareIndex, y + checkerBoardSquareSize, x + squareIndex + checkerBoardSquareSize, y + height, 0xFF909090);

				if (squareIndex < width - checkerBoardSquareSize) {
					int minX = x + squareIndex + checkerBoardSquareSize;
					int maxX = Math.min(x + width, x + squareIndex + checkerBoardSquareSize * 2);

					Gui.drawRect(minX, y, maxX, y + height, 0xFF909090);
					Gui.drawRect(minX,y + checkerBoardSquareSize, maxX, y + height, 0xFFFFFFFF);
				}
			}

			left = !left;
		}

		RenderHelper.drawLeftGradientRect(x, y, x + width, y + height, new Color(red, green, blue, 1).getRGB(), 0);

		int sliderMinX = (int) (x + width - (width * alpha));

		Gui.drawRect(sliderMinX - 1, y,  sliderMinX + 1, y + height, -1);
	}

	public static void drawColorPicker(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
		drawPicker(setting, mouseX, mouseY, x + 3, y + height + (boost * height) + 2, x + 3, y + height + (boost * height) + 103, x + 3, y + height + (boost * height) + 115);
		setting.setValue(finalColor);
	}
}
