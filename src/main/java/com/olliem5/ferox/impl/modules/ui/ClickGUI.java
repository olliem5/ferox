package com.olliem5.ferox.impl.modules.ui;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.gui.screens.click.ClickGUIScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

/**
 * @author olliem5
 */

@FeroxModule(name = "ClickGUI", description = "Opens Ferox's ClickGUI", category = Category.Interface, key = Keyboard.KEY_P)
public final class ClickGUI extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", "The theme to use for the ClickGUI", ThemeModes.Default);
    public static final Setting<BackgroundModes> backgroundMode = new Setting<>("Background", "The background to render when you are in the ClickGUI", BackgroundModes.Blur);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", "Allows windows to go over the screen", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", "Speed to scroll the windows at", 0, 10, 20, 0);

    public static final Setting<Boolean> descriptions = new Setting<>("Descriptions", "Handles description rendering in the ClickGUI", true);
    public static final Setting<Boolean> moduleDescriptions = new Setting<>(descriptions, "Module Descriptions", "Shows module descriptions in the bottom left corner", true);
    public static final Setting<Boolean> settingDescriptions = new Setting<>(descriptions, "Setting Descriptions", "Shows setting descriptions in the bottom left corner", true);
    public static final Setting<Boolean> drawnDescriptions = new Setting<>(descriptions, "Drawn Descriptions", "Shows drawn descriptions in the bottom left corner", true);
    public static final Setting<Boolean> keybindDescriptions = new Setting<>(descriptions, "Keybind Descriptions", "Shows keybind descriptions in the bottom left corner", true);

    public static final Setting<NameModes> nameMode = new Setting<>("Names", "Changes how the names function", NameModes.Shrink);
    public static final Setting<IndicatorModes> indicatorMode = new Setting<>("Indicators", "Changes how the indicators function", IndicatorModes.Shrink);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", "Controls how the game is paused when the ClickGUI is open", PauseModes.Continue);

    public ClickGUI() {
        this.addSettings(
                theme,
                backgroundMode,
                windowOverflow,
                scrollSpeed,
                descriptions,
                nameMode,
                indicatorMode,
                pauseGame
        );
    }

    private ClickGUIScreen clickGUI;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (clickGUI == null) {
            clickGUI = new ClickGUIScreen();
        }

        if (OpenGlHelper.shadersSupported) {
            try {
                if (backgroundMode.getValue() == BackgroundModes.Blur) {
                    mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                }
            } catch (Exception ignored) {}
        }

        mc.displayGuiScreen(clickGUI);

        this.toggle();
    }

    public enum ThemeModes {
        Default
    }

    public enum BackgroundModes {
        Blur,
        Vanilla,
        None
    }

    public enum NameModes {
        Shrink,
        Stay
    }

    public enum IndicatorModes {
        Shrink,
        Stay,
        None
    }

    public enum PauseModes {
        Pause,
        Continue
    }
}
