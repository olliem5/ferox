package com.olliem5.ferox.api.util.client;

import com.google.gson.*;
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

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author olliem5
 * @author linustouchtips
 * @author Hoosiers
 *
 * This is pretty much a placeholder until I have time to completely rewrite this, something needed to be 'made' before the release.
 *
 * TODO: Sort modules into other folders for categories
 * TODO: Support Component settings
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
            saveFriends();
            saveEnemies();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void loadConfig() {
        try {
            createDirectory();
            loadModules();
            loadComponents();
            loadFriends();
            loadEnemies();
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
                                Setting<Enum> subEnumSetting = (Setting<Enum>) subSetting;
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
                    Setting<Enum> enumSetting = (Setting<Enum>) setting;
                    settingObject.add(enumSetting.getName(), new JsonPrimitive(String.valueOf(enumSetting.getValue())));

                    if (enumSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum> subEnumSetting = (Setting<Enum>) subSetting;
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
                                Setting<Enum> subEnumSetting = (Setting<Enum>) subSetting;
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
                                Setting<Enum> subEnumSetting = (Setting<Enum>) subSetting;
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
                                Setting<Enum> subEnumSetting = (Setting<Enum>) subSetting;
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

            if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Bind") == null) continue;

            JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            for (Setting<?> setting : module.getSettings()) {
                //Load module stuff
            }

            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            module.setKey((moduleObject.get("Bind").getAsInt()));
        }
    }

    public static void saveComponents() throws IOException {
        registerFiles("HUD", "gui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ferox/gui/HUD.json"), StandardCharsets.UTF_8);

        JsonObject guiObject = new JsonObject();
        JsonObject hudObject = new JsonObject();

        for (Component component : ComponentManager.getComponents()) {
            JsonObject positionObject = new JsonObject();

            positionObject.add("x", new JsonPrimitive(component.getPosX()));
            positionObject.add("y", new JsonPrimitive(component.getPosY()));
            positionObject.add("visible", new JsonPrimitive(component.isVisible()));

            hudObject.add(component.getName(), positionObject);
        }

        guiObject.add("Components", hudObject);

        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadComponents() throws IOException {
        if (!Files.exists(Paths.get("ferox/gui/HUD.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("ferox/gui/HUD.json"));
        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (guiObject.get("Components") == null) return;

        JsonObject windowObject = guiObject.get("Components").getAsJsonObject();

        for (Component component : ComponentManager.getComponents()) {
            if (windowObject.get(component.getName()) == null) return;

            JsonObject categoryObject = windowObject.get(component.getName()).getAsJsonObject();

            JsonElement hudXObject = categoryObject.get("x");

            if (hudXObject != null && hudXObject.isJsonPrimitive()) {
                component.setPosX(hudXObject.getAsInt());
            }

            JsonElement hudYObject = categoryObject.get("y");

            if (hudYObject != null && hudYObject.isJsonPrimitive()) {
                component.setPosY(hudYObject.getAsInt());
            }

            JsonElement hudVisibleObject = categoryObject.get("visible");

            if (hudVisibleObject != null && hudVisibleObject.isJsonPrimitive()) {
                if (hudVisibleObject.getAsBoolean()) {
                    component.setVisible(true);
                }
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
}
