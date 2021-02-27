package com.olliem5.ferox.impl.modules.ui;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.gui.screens.editor.HUDEditorScreen;
import net.minecraft.util.ResourceLocation;

/**
 * @author olliem5
 */

@FeroxModule(name = "HUDEditor", description = "Opens Ferox's HUDEditor", category = Category.Interface)
public final class HUDEditor extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", "The theme to use for the HUDEditor", ThemeModes.Default);
    public static final Setting<BackgroundModes> backgroundMode = new Setting<>("Background", "The background to render when you are in the HUDEditor", BackgroundModes.Blur);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", "Allows windows to go over the screen", false);
    public static final Setting<Boolean> componentOverflow = new Setting<>("Component Overflow", "Allows components to go over the screen", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", "Speed to scroll the windows at", 0, 10, 20, 0);

    public static final Setting<Boolean> descriptions = new Setting<>("Descriptions", "Handles description rendering in the HUDEditor", true);
    public static final Setting<Boolean> componentDescriptions = new Setting<>(descriptions, "Component Descriptions", "Shows component descriptions in the bottom left corner", true);
    public static final Setting<Boolean> settingDescriptions = new Setting<>(descriptions, "Setting Descriptions", "Shows setting descriptions in the bottom left corner", true);

    public static final Setting<NameModes> nameMode = new Setting<>("Names", "Changes how the names function", NameModes.Shrink);
    public static final Setting<IndicatorModes> indicatorMode = new Setting<>("Indicators", "Changes how the indicators function", IndicatorModes.Shrink);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", "Controls how the game is paused when the HUDEditor is open", PauseModes.Continue);

    public HUDEditor() {
        this.addSettings(
                theme,
                backgroundMode,
                windowOverflow,
                componentOverflow,
                scrollSpeed,
                descriptions,
                nameMode,
                indicatorMode,
                pauseGame
        );
    }

    private HUDEditorScreen hudEditor;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (hudEditor == null) {
            hudEditor = new HUDEditorScreen();
        }

        if (backgroundMode.getValue() == BackgroundModes.Blur) {
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }

        mc.displayGuiScreen(hudEditor);

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
