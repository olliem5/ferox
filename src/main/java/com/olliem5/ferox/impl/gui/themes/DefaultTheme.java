package com.olliem5.ferox.impl.gui.themes;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.theme.FeroxTheme;
import com.olliem5.ferox.api.theme.Theme;
import com.olliem5.ferox.api.util.colour.ColourUtil;
import com.olliem5.ferox.api.util.math.EnumUtil;
import com.olliem5.ferox.api.util.math.MathUtil;
import com.olliem5.ferox.api.util.render.draw.DrawUtil;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.api.util.render.gui.GuiUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;
import com.olliem5.ferox.impl.modules.ui.ClickGUI;
import com.olliem5.ferox.impl.modules.ui.HUDEditor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author olliem5
 * @author bon
 * @author linustouchtips
 */

@FeroxTheme(name = "Default", width = 105, height = 14)
public final class DefaultTheme extends Theme {
	//TODO: Remove these (maybe)!
	public static final int width = 105;
	public static final int height = 14;

	private static int boost = 0;

	public static Color finalColor;

	@Override
	public void drawTitles(String name, int x, int y) {
		Gui.drawRect(x, y, (x + width), y + height, 0xFF2F2F2F);
		Gui.drawRect(x, y + height, (x + width), y + height + 1, Colours.clientColourPicker.getValue().getRGB());

		FontUtil.drawText(name, (x + ((x + width) - x) / 2 - FontUtil.getStringWidth(name) / 2), y + 3, -1);
	}

	@Override
	public void drawModules(ArrayList<Module> modules, int x, int y, int mouseX, int mouseY) {
		boost = 0;

		for (Module module : modules) {
			int color = 0xFF212121;

			if (module.isEnabled()) {
				color = 0xFF2F2F2F;
			}

			Gui.drawRect(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + 1 + (boost * height), color);

			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (GuiUtil.leftDown) {
					module.toggle();
				}

				if (GuiUtil.rightDown) {
					module.setOpened(!module.isOpened());
				}

				if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
					FontUtil.drawText(module.getName(), x + 3, y + height + 4 + (boost * height), -1);
				} else {
					FontUtil.drawText(module.getName(), x + 2, y + height + 4 + (boost * height), -1);
				}

				if (module.hasSettings()) {
					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}

				if (ClickGUI.descriptions.getValue() && ClickGUI.moduleDescriptions.getValue()) {
					drawModuleBottomLeftText(module);
				}
			} else {
				FontUtil.drawText(module.getName(), x + 2, y + height + 4 + (boost * height), -1);

				if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					if (module.hasSettings()) {
						FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
					}
				}
			}

			if (module.isOpened()) {
				if (module.hasSettings()) {
					drawModuleDropdown(module, x, y + 1, mouseX, mouseY);
				}

				boost++;

				renderDrawn(module, x, y + 1);

				boost++;

				renderKeybind(module, GuiUtil.keyDown, x, y + 1);
			}

			boost++;
		}
	}

	@Override
	public void drawComponents(ArrayList<Component> components, int x, int y, int mouseX, int mouseY) {
		boost = 0;

		for (Component component : components) {
			int color = 0xFF212121;

			if (component.isVisible()) {
				color = 0xFF2F2F2F;
			}

			Gui.drawRect(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + 1 + (boost * height), color);

			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (GuiUtil.leftDown) {
					component.setVisible(!component.isVisible());
				}

				if (GuiUtil.rightDown) {
					component.setOpened(!component.isOpened());
				}

				if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
					FontUtil.drawText(component.getName(), x + 3, y + height + 4 + (boost * height), -1);
				} else {
					FontUtil.drawText(component.getName(), x + 2, y + height + 4 + (boost * height), -1);
				}

				if (component.hasSettings()) {
					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}

				if (HUDEditor.descriptions.getValue() && HUDEditor.componentDescriptions.getValue()) {
					drawComponentBottomLeftText(component);
				}
			} else {
				FontUtil.drawText(component.getName(), x + 2, y + height + 4 + (boost * height), -1);

				if (component.hasSettings()) {
					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
					}
				}
			}

			if (component.isOpened()) {
				if (component.hasSettings()) {
					drawComponentDropdown(component, x, y + 1, mouseX, mouseY);
				}
			}

			boost++;
		}
	}

	public static void drawModuleDropdown(Module module, int x, int y, int mouseX, int mouseY) {
		for (Setting<?> setting : module.getSettings()) {
			boost++;

			if (setting.getValue() instanceof Boolean) {
				Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
				renderBoolean(booleanSetting, x, y, false);
			}

			if (setting.getValue() instanceof Enum) {
				Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
				renderEnum(enumSetting, x, y, false);
			}

			if (setting.getValue() instanceof Color) {
				Setting<Color> colorSetting = (Setting<Color>) setting;
				drawColourPicker(colorSetting, x, y, mouseX, mouseY);

				boost += 8;
			}

			if (setting.getValue() instanceof Integer) {
				NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) setting;
				renderInteger(integerNumberSetting, x, y, false);
			}

			if (setting.getValue() instanceof Double) {
				NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) setting;
				renderDouble(doubleNumberSetting, x, y, false);
			}

			if (setting.getValue() instanceof Float) {
				NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) setting;
				renderFloat(floatNumberSetting, x, y, false);
			}

			if (setting.isOpened()) {
				for (Setting<?> subSetting : setting.getSubSettings()) {
					boost++;

					if (subSetting.getValue() instanceof Boolean) {
						Setting<Boolean> booleanSubSetting = (Setting<Boolean>) subSetting;
						renderSubBoolean(booleanSubSetting, x, y, false);
					}

					if (subSetting.getValue() instanceof Enum) {
						Setting<Enum<?>> enumSubSetting = (Setting<Enum<?>>) subSetting;
						renderSubEnum(enumSubSetting, x, y, false);
					}

					if (subSetting.getValue() instanceof Color) {
						Setting<Color> colorSubSetting = (Setting<Color>) subSetting;
						drawColourPicker(colorSubSetting, x, y, mouseX, mouseY);

						boost += 8;
					}

					if (subSetting.getValue() instanceof Integer) {
						NumberSetting<Integer> integerSubSetting = (NumberSetting<Integer>) subSetting;
						renderSubInteger(integerSubSetting, x, y, false);
					}

					if (subSetting.getValue() instanceof Double) {
						NumberSetting<Double> doubleSubSetting = (NumberSetting<Double>) subSetting;
						renderSubDouble(doubleSubSetting, x, y, false);
					}

					if (subSetting.getValue() instanceof Float) {
						NumberSetting<Float> floatSubSetting = (NumberSetting<Float>) subSetting;
						renderSubFloat(floatSubSetting, x, y, false);
					}
				}
			}
		}
	}

	public static void drawComponentDropdown(Component component, int x, int y, int mouseX, int mouseY) {
		for (Setting<?> setting : component.getSettings()) {
			boost++;

			if (setting.getValue() instanceof Boolean) {
				Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
				renderBoolean(booleanSetting, x, y, true);
			}

			if (setting.getValue() instanceof Enum) {
				Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
				renderEnum(enumSetting, x, y, true);
			}

			if (setting.getValue() instanceof Color) {
				Setting<Color> colorSetting = (Setting<Color>) setting;
				drawColourPicker(colorSetting, x, y, mouseX, mouseY);

				boost += 8;
			}

			if (setting.getValue() instanceof Integer) {
				NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) setting;
				renderInteger(integerNumberSetting, x, y, true);
			}

			if (setting.getValue() instanceof Double) {
				NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) setting;
				renderDouble(doubleNumberSetting, x, y, true);
			}

			if (setting.getValue() instanceof Float) {
				NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) setting;
				renderFloat(floatNumberSetting, x, y, true);
			}

			if (setting.isOpened()) {
				for (Setting<?> subSetting : setting.getSubSettings()) {
					boost++;

					if (subSetting.getValue() instanceof Boolean) {
						Setting<Boolean> booleanSubSetting = (Setting<Boolean>) subSetting;
						renderSubBoolean(booleanSubSetting, x, y, true);
					}

					if (subSetting.getValue() instanceof Enum) {
						Setting<Enum<?>> enumSubSetting = (Setting<Enum<?>>) subSetting;
						renderSubEnum(enumSubSetting, x, y, true);
					}

					if (subSetting.getValue() instanceof Color) {
						Setting<Color> colorSubSetting = (Setting<Color>) subSetting;
						drawColourPicker(colorSubSetting, x, y, mouseX, mouseY);

						boost += 8;
					}

					if (subSetting.getValue() instanceof Integer) {
						NumberSetting<Integer> integerSubSetting = (NumberSetting<Integer>) subSetting;
						renderSubInteger(integerSubSetting, x, y, true);
					}

					if (subSetting.getValue() instanceof Double) {
						NumberSetting<Double> doubleSubSetting = (NumberSetting<Double>) subSetting;
						renderSubDouble(doubleSubSetting, x, y, true);
					}

					if (subSetting.getValue() instanceof Float) {
						NumberSetting<Float> floatSubSetting = (NumberSetting<Float>) subSetting;
						renderSubFloat(floatSubSetting, x, y, true);
					}
				}
			}
		}
	}

	private static void renderBoolean(Setting<Boolean> setting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		if (setting.getValue()) {
			color = 0xFF2F2F2F;
		}

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				setting.setValue(!setting.getValue());
			}

			if (GuiUtil.rightDown) {
				setting.setOpened(!setting.isOpened());
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (!hud) {
					if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
						FontUtil.drawText(setting.getName(), x + 5, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);
					}

					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				} else {
					if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
						FontUtil.drawText(setting.getName(), x + 5, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);
					}

					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}
			} else {
				FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);

				if (!hud && ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				} else if (hud && HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}
		} else {
			FontUtil.drawText(setting.getName(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubBoolean(Setting<Boolean> subSetting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		if (subSetting.getValue()) {
			color = 0xFF2F2F2F;
		}

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				subSetting.setValue(!subSetting.getValue());
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(subSetting.getName(), x + 6, (y + height) + 3 + (boost * height), -1);
	}
	
	private static void renderEnum(Setting<Enum<?>> setting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				EnumUtil.setEnumValue(setting, EnumUtil.getNextEnumValue(setting, false));
			}

			if (GuiUtil.rightDown) {
				setting.setOpened(setting.isOpened());
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (!hud) {
					if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				} else {
					if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);

				if (!hud && ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				} else if (hud && HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubEnum(Setting<Enum<?>> subSetting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				EnumUtil.setEnumValue(subSetting, EnumUtil.getNextEnumValue(subSetting, false));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		FontUtil.drawText(subSetting.getName() + ChatFormatting.GRAY + " " + subSetting.getValue(), x + 6, (y + height) + 3 + (boost * height), -1);
	}

	private static void renderInteger(NumberSetting<Integer> setting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = ((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				setting.setValue((int) (percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (!hud) {
					if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				} else {
					if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);

				if (!hud && ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				} else if (hud && HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubInteger(NumberSetting<Integer> subSetting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = ((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin());

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue((int) (percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName() + ChatFormatting.GRAY + " " + subSetting.getValue(), x + 6, (y + height) + 3 + (boost * height), -1);
	}

	private static void renderDouble(NumberSetting<Double> setting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				setting.setValue(MathUtil.roundNumber(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (!hud) {
					if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				} else {
					if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);

				if (!hud && ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				} else if (hud && HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubDouble(NumberSetting<Double> subSetting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue(MathUtil.roundNumber(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName() + ChatFormatting.GRAY + " " + subSetting.getValue(), x + 6, (y + height) + 3 + (boost * height), -1);
	}

	private static void renderFloat(NumberSetting<Float> setting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				setting.setValue((float) MathUtil.roundNumber(percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin(), setting.getScale()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(setting);
				}
			}
		}

		if (pixAdd < 1) {
			pixAdd = 1;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 1, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		if (setting.hasSubSettings()) {
			if (GuiUtil.mouseOver(x, y + height + 1 + (boost * height), (x + width), y + height * 2 + (boost * height))) {
				if (!hud) {
					if (ClickGUI.nameMode.getValue() == ClickGUI.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
						if (ClickGUI.indicatorMode.getValue() == ClickGUI.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				} else {
					if (HUDEditor.nameMode.getValue() == HUDEditor.NameModes.Shrink) {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
					} else {
						FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 5, (y + height) + 3 + (boost * height), -1);
					}

					if (HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
						if (HUDEditor.indicatorMode.getValue() == HUDEditor.IndicatorModes.Shrink) {
							FontUtil.drawText("...", (x + width) - 11, y + height + 3 + (boost * height), -1);
						} else {
							FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
						}
					}
				}
			} else {
				FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);

				if (!hud && ClickGUI.indicatorMode.getValue() != ClickGUI.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				} else if (hud && HUDEditor.indicatorMode.getValue() != HUDEditor.IndicatorModes.None) {
					FontUtil.drawText("...", (x + width) - 10, y + height + 3 + (boost * height), -1);
				}
			}
		} else {
			FontUtil.drawText(setting.getName() + ChatFormatting.GRAY + " " + setting.getValue(), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	private static void renderSubFloat(NumberSetting<Float> subSetting, int x, int y, boolean hud) {
		int color = 0xFF212121;

		int pixAdd = (int) (((x + width) - x) * (subSetting.getValue() - subSetting.getMin()) / (subSetting.getMax() - subSetting.getMin()));

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftHeld) {
				int percentError = (GuiUtil.mouseX - (x)) * 100 / ((x + width) - x);
				subSetting.setValue((float) MathUtil.roundNumber(percentError * ((subSetting.getMax() - subSetting.getMin()) / 100.0D) + subSetting.getMin(), subSetting.getScale()));
			}

			if (!hud) {
				if (ClickGUI.descriptions.getValue() && ClickGUI.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			} else {
				if (HUDEditor.descriptions.getValue() && HUDEditor.settingDescriptions.getValue()) {
					drawSettingBottomLeftText(subSetting);
				}
			}
		}

		if (pixAdd < 2) {
			pixAdd = 2;
		}

		Gui.drawRect(x, y + height + (boost * height), x + 2, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 2, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);
		Gui.drawRect(x + 2, y + height + (boost * height), (x) + pixAdd, (y + height) + height + (boost * height), 0xFF2F2F2F);

		FontUtil.drawText(subSetting.getName() + ChatFormatting.GRAY + " " + subSetting.getValue(), x + 6, (y + height) + 3 + (boost * height), -1);
	}

	public static void renderDrawn(Module module, int x, int y) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				module.setDrawn(!module.isDrawn());
			}

			if (ClickGUI.descriptions.getValue() && ClickGUI.drawnDescriptions.getValue()) {
				drawDrawnBottomLeftText(module);
			}
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		if (module.isDrawn()) {
			FontUtil.drawText("Drawn" + ChatFormatting.GRAY + " True", x + 4, (y + height) + 3 + (boost * height), -1);
		} else {
			FontUtil.drawText("Drawn" + ChatFormatting.GRAY + " False", x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	public static void renderKeybind(Module module, int key, int x, int y) {
		int color = 0xFF212121;

		if (GuiUtil.mouseOver(x, y + height + (boost * height), (x + width), (y + height) + height - 1 + (boost * height))) {
			if (GuiUtil.leftDown) {
				module.setBinding(!module.isBinding());
			}

			if (ClickGUI.descriptions.getValue() && ClickGUI.keybindDescriptions.getValue()) {
				drawKeybindBottomLeftText(module, Keyboard.getKeyName(module.getKey()));
			}
		}

		if (module.isBinding() && key != -1 && key != Keyboard.KEY_ESCAPE && key != Keyboard.KEY_DELETE) {
			module.setKey((key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK) ? Keyboard.KEY_NONE : key);
			module.setBinding(false);
		}

		if (module.isBinding() && key == Keyboard.KEY_ESCAPE) {
			module.setBinding(false);
		}

		Gui.drawRect(x, y + height + (boost * height), x + 1, (y + height) + height + (boost * height), Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(x + 1, y + height + (boost * height), (x + width), (y + height) + height + (boost * height), color);

		if (module.isBinding()) {
			FontUtil.drawText("Listening" + ChatFormatting.GRAY + "...", x + 4, (y + height) + 3 + (boost * height), -1);
		} else {
			FontUtil.drawText("Keybind" + ChatFormatting.GRAY + " " + Keyboard.getKeyName(module.getKey()), x + 4, (y + height) + 3 + (boost * height), -1);
		}
	}

	public static void drawModuleBottomLeftText(Module module) {
		FontUtil.drawText(module.getDescription(), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getStringHeight(module.getDescription()) - 2), -1);
	}

	public static void drawComponentBottomLeftText(Component component) {
		FontUtil.drawText(component.getDescription(), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getStringHeight(component.getDescription()) - 2), -1);
	}

	public static void drawSettingBottomLeftText(Setting<?> setting) {
		FontUtil.drawText(setting.getDescription(), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getStringHeight(setting.getDescription()) - 2), -1);
	}

	public static void drawDrawnBottomLeftText(Module module) {
		FontUtil.drawText(module.getName() + "is " + (module.isDrawn() ? "drawn" : "not drawn"), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getStringHeight(module.getName() + "is " + (module.isDrawn() ? "drawn" : "not drawn")) - 2), -1);
	}

	public static void drawKeybindBottomLeftText(Module module, String key) {
		FontUtil.drawText(module.getName() + "'s keybind is " + ChatFormatting.GRAY + key, 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getStringHeight(module.getName() + "'s keybind is " + ChatFormatting.GRAY + key) - 2), -1);
	}

	public static void drawPicker(Setting<Color> colourSetting, int mouseX, int mouseY, int pickerX, int pickerY, int hueSliderX, int hueSliderY, int alphaSliderX, int alphaSliderY) {
		float[] color = new float[] {
				Color.RGBtoHSB(colourSetting.getValue().getRed(), colourSetting.getValue().getGreen(), colourSetting.getValue().getBlue(), null)[0],
				Color.RGBtoHSB(colourSetting.getValue().getRed(), colourSetting.getValue().getGreen(), colourSetting.getValue().getBlue(), null)[1],
				Color.RGBtoHSB(colourSetting.getValue().getRed(), colourSetting.getValue().getGreen(), colourSetting.getValue().getBlue(), null)[2]
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

		if (GuiUtil.leftHeld && GuiUtil.mouseOver(pickerX, pickerY, pickerX + pickerWidth, pickerY + pickerHeight)) {
			pickingColor = true;
		}

		if (GuiUtil.leftHeld && GuiUtil.mouseOver(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + hueSliderHeight)) {
			pickingHue = true;
		}

		if (GuiUtil.leftHeld && GuiUtil.mouseOver(alphaSliderX, alphaSliderY, alphaSliderX + alphaSliderWidth, alphaSliderY + alphaSliderHeight)) {
			pickingAlpha = true;
		}

		if (pickingHue) {
			float restrictedX = (float) Math.min(Math.max(hueSliderX, mouseX), hueSliderX + hueSliderWidth);
			color[0] = (restrictedX - (float) hueSliderX) / hueSliderWidth;
		}

		if (pickingAlpha) {
			float restrictedX = (float) Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + alphaSliderWidth);
			colourSetting.setAlpha(1 - (restrictedX - (float) alphaSliderX) / alphaSliderWidth);
		}

		if (pickingColor) {
			float restrictedX = (float) Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth);
			float restrictedY = (float) Math.min(Math.max(pickerY, mouseY), pickerY + pickerHeight);

			color[1] = (restrictedX - (float) pickerX) / pickerWidth;
			color[2] = 1 - (restrictedY - (float) pickerY) / pickerHeight;
		}

		Gui.drawRect(pickerX - 3, pickerY - 2, pickerX + pickerWidth + 2, pickerY + pickerHeight + 30, Colours.clientColourPicker.getValue().getRGB());
		Gui.drawRect(pickerX - 2, pickerY - 2, pickerX + pickerWidth + 2, pickerY + pickerHeight + 30, 0xFF212121);

		int selectedColor = Color.HSBtoRGB(color[0], 1.0f, 1.0f);

		float selectedRed = (selectedColor >> 16 & 0xFF) / 255.0f;
		float selectedGreen = (selectedColor >> 8 & 0xFF) / 255.0f;
		float selectedBlue = (selectedColor & 0xFF) / 255.0f;

		DrawUtil.drawPickerBase(pickerX, pickerY, pickerWidth, pickerHeight, selectedRed, selectedGreen, selectedBlue, colourSetting.getAlpha());

		drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight, color[0]);

		int cursorX = (int) (pickerX + color[1] * pickerWidth);
		int cursorY = (int) ((pickerY + pickerHeight) - color[2] * pickerHeight);

		Gui.drawRect(cursorX - 2, cursorY - 2, cursorX + 2, cursorY + 2, -1);

		drawAlphaSlider(alphaSliderX, alphaSliderY, alphaSliderWidth, alphaSliderHeight, selectedRed, selectedGreen, selectedBlue, colourSetting.getAlpha());

		finalColor = ColourUtil.integrateAlpha(new Color(Color.HSBtoRGB(color[0], color[1], color[2])), colourSetting.getAlpha());
	}

	public static void drawHueSlider(int x, int y, int width, int height, float hue) {
		int step = 0;

		if (height > width) {
			Gui.drawRect(x, y, x + width, y + 4, 0xFFFF0000);

			y += 4;

			for (int colorIndex = 0; colorIndex < 5; colorIndex++) {
				int previousStep = Color.HSBtoRGB((float) step / 5, 1.0f, 1.0f);
				int nextStep = Color.HSBtoRGB((float) (step + 1) / 5, 1.0f, 1.0f);

				DrawUtil.drawGradientRect(x, y + step * (height / 5), x + width, y + (step + 1) * (height / 5), previousStep, nextStep);

				step++;
			}

			int sliderMinY = (int) (y + (height * hue)) - 4;

			Gui.drawRect(x, sliderMinY - 1, x + width, sliderMinY + 1, -1);
		} else {
			for (int colorIndex = 0; colorIndex < 5; colorIndex++) {
				int previousStep = Color.HSBtoRGB((float) step / 5, 1.0f, 1.0f);
				int nextStep = Color.HSBtoRGB((float) (step + 1) / 5, 1.0f, 1.0f);

				DrawUtil.gradient(x + step * (width / 5), y, x + (step + 1) * (width / 5), y + height, previousStep, nextStep, true);

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

		DrawUtil.drawLeftGradientRect(x, y, x + width, y + height, new Color(red, green, blue, 1).getRGB(), 0);

		int sliderMinX = (int) (x + width - (width * alpha));

		Gui.drawRect(sliderMinX - 1, y,  sliderMinX + 1, y + height, -1);
	}

	public static void drawColourPicker(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
		drawPicker(setting, mouseX, mouseY, x + 3, y + height + (boost * height) + 2, x + 3, y + height + (boost * height) + 103, x + 3, y + height + (boost * height) + 115);
		setting.setValue(finalColor);
	}
}
