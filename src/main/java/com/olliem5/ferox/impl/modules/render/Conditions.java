package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;

/**
 * @author olliem5
 */

@FeroxModule(name = "Conditions", description = "Changes the world time and weather", category = Category.Render)
public final class Conditions extends Module {
    public static final Setting<WeatherModes> weatherMode = new Setting<>("Weather", "The world's weather", WeatherModes.Clear);

    public static final Setting<TimeModes> timeMode = new Setting<>("Time", "The world's time", TimeModes.Midnight);
    public static final NumberSetting<Integer> customTime = new NumberSetting<>("Custom Time", "The custom time value", 0, 18000, 24000, 0);

    public Conditions() {
        this.addSettings(
                weatherMode,
                timeMode,
                customTime
        );
    }

    public void onUpdate() {
        if (nullCheck()) return;

        switch (weatherMode.getValue()) {
            case Clear:
                mc.world.setRainStrength(0);
                break;
            case Rain:
                mc.world.setRainStrength(1);
                break;
            case Thunder:
                mc.world.setRainStrength(2);
                break;
        }

        switch (timeMode.getValue()) {
            case Day:
                mc.world.setWorldTime(1000);
                break;
            case Noon:
                mc.world.setWorldTime(6000);
                break;
            case Sunset:
                mc.world.setWorldTime(12500);
                break;
            case Night:
                mc.world.setWorldTime(13000);
                break;
            case Midnight:
                mc.world.setWorldTime(18000);
                break;
            case Sunrise:
                mc.world.setWorldTime(23500);
                break;
            case Custom:
                mc.world.setWorldTime(customTime.getValue());
                break;
        }
    }

    @Override
    public String getArraylistInfo() {
        return weatherMode.getValue().toString() + ", " + timeMode.getValue().toString();
    }

    public enum WeatherModes {
        Clear,
        Rain,
        Thunder
    }

    public enum TimeModes {
        Day,
        Noon,
        Sunset,
        Night,
        Midnight,
        Sunrise,
        Custom
    }
}
