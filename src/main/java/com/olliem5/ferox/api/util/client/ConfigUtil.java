package com.olliem5.ferox.api.util.client;

import com.google.gson.*;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.ComponentManager;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.setting.NumberSetting;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.social.enemy.Enemy;
import com.olliem5.ferox.api.social.enemy.EnemyManager;
import com.olliem5.ferox.api.social.friend.Friend;
import com.olliem5.ferox.api.social.friend.FriendManager;
import com.olliem5.ferox.api.util.math.EnumUtil;
import com.olliem5.ferox.impl.gui.screens.click.ClickGUIWindow;
import com.olliem5.ferox.impl.gui.screens.editor.HUDEditorWindow;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author olliem5
 * @author linustouchtips
 * @author Hoosiers
 */

public final class ConfigUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static void createDirectory() throws IOException {
        if (!Files.exists(Paths.get("ferox/"))) {
            Files.createDirectories(Paths.get("ferox/"));
        }

        if (!Files.exists(Paths.get("ferox/modules"))) {
            Files.createDirectories(Paths.get("ferox/modules"));
        }

        if (!Files.exists(Paths.get("ferox/components"))) {
            Files.createDirectories(Paths.get("ferox/components"));
        }

        if (!Files.exists(Paths.get("ferox/gui"))) {
            Files.createDirectories(Paths.get("ferox/gui"));
        }

        if (!Files.exists(Paths.get("ferox/social"))) {
            Files.createDirectories(Paths.get("ferox/social"));
        }
    }

    public static void registerFiles(String name, String path) throws IOException {
        if (Files.exists(Paths.get("ferox/" + path + "/" + name + ".json"))) {
            File file = new File("ferox/" + path + "/" + name + ".json");
            file.delete();
        }

        Files.createFile(Paths.get("ferox/" + path + "/" + name + ".json"));
    }

    public static void saveConfig() {
        try {
            saveModules();
            saveComponents();
            saveClickGUI();
            saveHUDEditor();
            saveFriends();
            saveEnemies();
            savePrefix();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void loadConfig() {
        try {
            createDirectory();
            loadModules();
            loadComponents();
            loadClickGUI();
            loadHUDEditor();
            loadFriends();
            loadEnemies();
            loadPrefix();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void saveModules() throws IOException {
        for (Module module : ModuleManager.getModules()) {
            registerFiles(module.getName(), "modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(module.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
            moduleObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
            moduleObject.add("Bind", new JsonPrimitive(module.getKey()));

            for (Setting<?> setting : module.getSettings()) {
                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingObject.add(booleanSetting.getName(), new JsonPrimitive(booleanSetting.getValue()));

                    if (booleanSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : booleanSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingObject.add(enumSetting.getName(), new JsonPrimitive(String.valueOf(enumSetting.getValue())));

                    if (enumSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingObject.add(integerSetting.getName(), new JsonPrimitive(integerSetting.getValue()));

                    if (integerSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : integerSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingObject.add(doubleSetting.getName(), new JsonPrimitive(doubleSetting.getValue()));

                    if (doubleSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : doubleSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingObject.add(floatSetting.getName(), new JsonPrimitive(floatSetting.getValue()));

                    if (floatSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : floatSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }
            }

            moduleObject.add("Settings", settingObject);
            settingObject.add("SubSettings", subSettingObject);

            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));

            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    public static void loadModules() throws IOException {
        for (Module module : ModuleManager.getModules()) {
            if (!Files.exists(Paths.get("ferox/modules/" + module.getName() + ".json"))) continue;

            InputStream inputStream = Files.newInputStream(Paths.get("ferox/modules/" + module.getName() + ".json"));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Drawn") == null || moduleObject.get("Bind") == null) continue;

            JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            for (Setting<?> setting : module.getSettings()) {
                JsonElement settingValueObject = null;

                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingValueObject = settingObject.get(booleanSetting.getName());

                    for (Setting<?> subSetting : booleanSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingValueObject = settingObject.get(enumSetting.getName());

                    for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingValueObject = settingObject.get(integerSetting.getName());

                    for (Setting<?> subSetting : integerSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingValueObject = settingObject.get(doubleSetting.getName());

                    for (Setting<?> subSetting : doubleSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingValueObject = settingObject.get(floatSetting.getName());

                    for (Setting<?> subSetting : floatSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (settingValueObject != null) {
                    if (setting.getValue() instanceof Boolean) {
                        Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                        booleanSetting.setValue(settingValueObject.getAsBoolean());
                    }

                    if (setting.getValue() instanceof Enum) {
                        Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                        EnumUtil.setEnumValue(enumSetting, settingValueObject.getAsString());
                    }

                    if (setting.getValue() instanceof Integer) {
                        NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                        integerSetting.setValue(settingValueObject.getAsInt());
                    }

                    if (setting.getValue() instanceof Double) {
                        NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                        doubleSetting.setValue(settingValueObject.getAsDouble());
                    }

                    if (setting.getValue() instanceof Float) {
                        NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                        floatSetting.setValue(settingValueObject.getAsFloat());
                    }
                }
            }

            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            module.setDrawn(moduleObject.get("Drawn").getAsBoolean());
            module.setKey((moduleObject.get("Bind").getAsInt()));
        }
    }

    public static void saveComponents() throws IOException {
        for (Component component : ComponentManager.getComponents()) {
            registerFiles(component.getName(), "components");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/components/" + component.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject componentObject = new JsonObject();
            JsonObject positionObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            componentObject.add("Name", new JsonPrimitive(component.getName()));
            componentObject.add("Visible", new JsonPrimitive(component.isVisible()));

            positionObject.add("X", new JsonPrimitive(component.getX()));
            positionObject.add("Y", new JsonPrimitive(component.getY()));

            for (Setting<?> setting : component.getSettings()) {
                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingObject.add(booleanSetting.getName(), new JsonPrimitive(booleanSetting.getValue()));

                    if (booleanSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : booleanSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingObject.add(enumSetting.getName(), new JsonPrimitive(String.valueOf(enumSetting.getValue())));

                    if (enumSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingObject.add(integerSetting.getName(), new JsonPrimitive(integerSetting.getValue()));

                    if (integerSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : integerSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingObject.add(doubleSetting.getName(), new JsonPrimitive(doubleSetting.getValue()));

                    if (doubleSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : doubleSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingObject.add(floatSetting.getName(), new JsonPrimitive(floatSetting.getValue()));

                    if (floatSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : floatSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }
            }

            componentObject.add("Position", positionObject);
            componentObject.add("Settings", settingObject);
            settingObject.add("SubSettings", subSettingObject);

            String jsonString = gson.toJson(new JsonParser().parse(componentObject.toString()));

            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    public static void loadComponents() throws IOException {
        for (Component component : ComponentManager.getComponents()) {
            if (!Files.exists(Paths.get("ferox/components/" + component.getName() + ".json"))) continue;

            InputStream inputStream = Files.newInputStream(Paths.get("ferox/components/" + component.getName() + ".json"));
            JsonObject componentObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (componentObject.get("Name") == null || componentObject.get("Visible") == null) continue;

            JsonObject positionObject = componentObject.get("Position").getAsJsonObject();
            JsonObject settingObject = componentObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            JsonElement xPositionObject = positionObject.get("X");
            JsonElement yPositionObject = positionObject.get("Y");

            component.setX(xPositionObject.getAsInt());
            component.setY(yPositionObject.getAsInt());

            for (Setting<?> setting : component.getSettings()) {
                JsonElement settingValueObject = null;

                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingValueObject = settingObject.get(booleanSetting.getName());

                    for (Setting<?> subSetting : booleanSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingValueObject = settingObject.get(enumSetting.getName());

                    for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingValueObject = settingObject.get(integerSetting.getName());

                    for (Setting<?> subSetting : integerSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingValueObject = settingObject.get(doubleSetting.getName());

                    for (Setting<?> subSetting : doubleSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingValueObject = settingObject.get(floatSetting.getName());

                    for (Setting<?> subSetting : floatSetting.getSubSettings()) {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting.getValue() instanceof Boolean) {
                            Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                            subSettingValueObject = subSettingObject.get(subBooleanSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Enum) {
                            Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                            subSettingValueObject = subSettingObject.get(subEnumSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Color) {
                            Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                            if (subSettingObject.get(subColourSetting.getName()).getAsJsonObject() == null) return;

                            JsonObject subColourObject = subSettingObject.get(subColourSetting.getName()).getAsJsonObject();

                            redValueObject = subColourObject.get("Red");
                            greenValueObject = subColourObject.get("Green");
                            blueValueObject = subColourObject.get("Blue");
                            alphaValueObject = subColourObject.get("Alpha");
                        }

                        if (subSetting.getValue() instanceof Integer) {
                            NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                            subSettingValueObject = subSettingObject.get(subIntegerSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Double) {
                            NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                            subSettingValueObject = subSettingObject.get(subDoubleSetting.getName());
                        }

                        if (subSetting.getValue() instanceof Float) {
                            NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                            subSettingValueObject = subSettingObject.get(subFloatSetting.getName());
                        }

                        if (subSettingValueObject != null) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subBooleanSetting.setValue(subSettingValueObject.getAsBoolean());
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                EnumUtil.setEnumValue(subEnumSetting, subSettingValueObject.getAsString());
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null) {
                                    subColourSetting.setValue(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                                }
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subIntegerSetting.setValue(subSettingValueObject.getAsInt());
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subDoubleSetting.setValue(subSettingValueObject.getAsDouble());
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subFloatSetting.setValue(subSettingValueObject.getAsFloat());
                            }
                        }
                    }
                }

                if (settingValueObject != null) {
                    if (setting.getValue() instanceof Boolean) {
                        Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                        booleanSetting.setValue(settingValueObject.getAsBoolean());
                    }

                    if (setting.getValue() instanceof Enum) {
                        Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                        EnumUtil.setEnumValue(enumSetting, settingValueObject.getAsString());
                    }

                    if (setting.getValue() instanceof Integer) {
                        NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                        integerSetting.setValue(settingValueObject.getAsInt());
                    }

                    if (setting.getValue() instanceof Double) {
                        NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                        doubleSetting.setValue(settingValueObject.getAsDouble());
                    }

                    if (setting.getValue() instanceof Float) {
                        NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                        floatSetting.setValue(settingValueObject.getAsFloat());
                    }
                }
            }

            component.setVisible(componentObject.get("Visible").getAsBoolean());
        }
    }

    public static void saveClickGUI() throws IOException {
        registerFiles("ClickGUI", "gui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/gui/ClickGUI.json"), StandardCharsets.UTF_8);

        JsonObject guiObject = new JsonObject();
        JsonObject windowObject = new JsonObject();

        for (ClickGUIWindow clickGUIWindow : ClickGUIWindow.windows) {
            JsonObject positionObject = new JsonObject();

            positionObject.add("X", new JsonPrimitive(clickGUIWindow.x));
            positionObject.add("Y", new JsonPrimitive(clickGUIWindow.y));
            positionObject.add("Open", new JsonPrimitive(clickGUIWindow.open));

            windowObject.add(clickGUIWindow.category.toString(), positionObject);
        }

        guiObject.add("Windows", windowObject);

        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadClickGUI() throws IOException {
        if (!Files.exists(Paths.get("ferox/gui/ClickGUI.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("ferox/gui/ClickGUI.json"));
        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (guiObject.get("Windows") == null) return;

        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();

        for (ClickGUIWindow clickGUIWindow : ClickGUIWindow.windows) {
            if (windowObject.get(clickGUIWindow.category.toString()) == null) return;

            JsonObject categoryObject = windowObject.get(clickGUIWindow.category.toString()).getAsJsonObject();

            JsonElement windowXObject = categoryObject.get("X");

            if (windowXObject != null && windowXObject.isJsonPrimitive()) {
                clickGUIWindow.setX(windowXObject.getAsInt());
            }

            JsonElement windowYObject = categoryObject.get("Y");

            if (windowYObject != null && windowYObject.isJsonPrimitive()) {
                clickGUIWindow.setY(windowYObject.getAsInt());
            }

            JsonElement windowOpenObject = categoryObject.get("Open");

            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive()) {
                clickGUIWindow.setOpen(windowOpenObject.getAsBoolean());
            }
        }

        inputStream.close();
    }

    public static void saveHUDEditor() throws IOException {
        registerFiles("HUDEditor", "gui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/gui/HUDEditor.json"), StandardCharsets.UTF_8);

        JsonObject guiObject = new JsonObject();
        JsonObject windowObject = new JsonObject();

        for (HUDEditorWindow hudEditorWindow : HUDEditorWindow.windows) {
            JsonObject positionObject = new JsonObject();

            positionObject.add("X", new JsonPrimitive(hudEditorWindow.x));
            positionObject.add("Y", new JsonPrimitive(hudEditorWindow.y));
            positionObject.add("Open", new JsonPrimitive(hudEditorWindow.open));

            windowObject.add("Ferox HUD", positionObject);
        }

        guiObject.add("Windows", windowObject);

        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadHUDEditor() throws IOException {
        if (!Files.exists(Paths.get("ferox/gui/HUDEditor.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("ferox/gui/HUDEditor.json"));
        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (guiObject.get("Windows") == null) return;

        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();

        for (HUDEditorWindow hudEditorWindow : HUDEditorWindow.windows) {
            if (windowObject.get("Ferox HUD") == null) return;

            JsonObject categoryObject = windowObject.get("Ferox HUD").getAsJsonObject();

            JsonElement windowXObject = categoryObject.get("X");

            if (windowXObject != null && windowXObject.isJsonPrimitive()) {
                hudEditorWindow.setX(windowXObject.getAsInt());
            }

            JsonElement windowYObject = categoryObject.get("Y");

            if (hudEditorWindow != null && windowYObject.isJsonPrimitive()) {
                hudEditorWindow.setY(windowYObject.getAsInt());
            }

            JsonElement windowOpenObject = categoryObject.get("Open");

            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive()) {
                hudEditorWindow.setOpen(windowOpenObject.getAsBoolean());
            }
        }

        inputStream.close();
    }

    public static void saveFriends() throws IOException {
        registerFiles("Friends", "Social");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/social/Friends.json"), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        JsonArray friendArray = new JsonArray();

        for (Friend friend : FriendManager.getFriends()) {
            friendArray.add(friend.getName());
        }

        mainObject.add("Friends", friendArray);

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadFriends() throws IOException {
        if (!Files.exists(Paths.get("ferox/social/Friends.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("ferox/social/Friends.json"));
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (mainObject.get("Friends") == null) return;

        JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();
        friendObject.forEach(object -> FriendManager.addFriend(object.getAsString()));

        inputStream.close();
    }

    public static void saveEnemies() throws IOException {
        registerFiles("Enemies", "social");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/social/Enemies.json"), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        JsonArray enemyArray = new JsonArray();

        for (Enemy enemy : EnemyManager.getEnemies()) {
            enemyArray.add(enemy.getName());
        }

        mainObject.add("Enemies", enemyArray);

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadEnemies() throws IOException {
        if (!Files.exists(Paths.get("ferox/social/Enemies.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("ferox/social/Enemies.json"));
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (mainObject.get("Enemies") == null) return;

        JsonArray enemyObject = mainObject.get("Enemies").getAsJsonArray();
        enemyObject.forEach(object -> EnemyManager.addEnemy(object.getAsString()));

        inputStream.close();
    }

    // using basic txt instead of json for command prefix
    // ... because its not enough information to justify using json

    static File prefixFile = new File("ferox/Prefix.txt");

    public static void savePrefix() throws IOException {
        if (!prefixFile.exists())
            prefixFile.createNewFile();

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(prefixFile), StandardCharsets.UTF_8);

        fileOutputStreamWriter.write(Ferox.CHAT_PREFIX);
        fileOutputStreamWriter.close();
    }


    public static void loadPrefix() throws IOException {
        if (!Files.exists(Paths.get("ferox/social/Prefix.txt"))) return;

        BufferedReader reader = new BufferedReader(new FileReader("ferox/social/Prefix.txt"));

        Ferox.CHAT_PREFIX = reader.readLine();
        reader.close();
    }
}
