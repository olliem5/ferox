package me.olliem5.ferox.api.util.client;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.olliem5.ferox.Ferox;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class ConfigUtil {
    public ConfigUtil() {
        init();
    }

    public void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject settingArray = new JsonObject();
        JsonObject moduleArray = new JsonObject();

        for (Module module : ModuleManager.getModules()) {
            JsonObject moduleSettings = new JsonObject();

            moduleSettings.addProperty("keybind", Keyboard.getKeyName(module.getKey()));
            moduleSettings.addProperty("enabled", module.isEnabled());

            /*
            for (Setting setting : Past.settingsManager.getSettingsModule(module)) {
                if (setting.getType() == "intslider") {
                    moduleSettings.addProperty(setting.getId(), setting.getValueInt());
                } else if (setting.getType() == "doubleslider") {
                    moduleSettings.addProperty(setting.getId(), setting.getValueDouble());
                } else if (setting.getType() == "boolean") {
                    moduleSettings.addProperty(setting.getId(), setting.getValBoolean());
                } else if (setting.getType() == "mode") {
                    moduleSettings.addProperty(setting.getId(), setting.getValueString());
                }
            }
            */

            moduleArray.add(module.getName(), moduleSettings);
        }
        settingArray.add("modules", moduleArray);

        try {
            FileWriter fileWriter = new FileWriter("FeroxConfig.json");
            gson.toJson(settingArray, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ioException) {
            System.out.println("[" + Ferox.NAME_VERSION + "]" + " " + "Config saving broke. Oops!");
        }
    }

    public void loadConfig() {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonReader jsonReader = new JsonReader(new FileReader("FeroxConfig.json"));
            JsonElement jsonElement = jsonParser.parse(jsonReader);

            JsonElement modulesMap = jsonElement.getAsJsonObject().get("modules");
            if (modulesMap != null) {
                for (Module module : ModuleManager.getModules()) {
                    JsonElement modulesMapTemp = modulesMap.getAsJsonObject().get(module.getName());
                    if (modulesMapTemp != null) {
                        module.setKey(Keyboard.getKeyIndex(modulesMapTemp.getAsJsonObject().get("keybind").getAsString()));

                        if (modulesMapTemp.getAsJsonObject().get("enabled").getAsBoolean() == true) {
                            module.toggle();
                        }

                        /*
                        for (Setting setting : Past.settingsManager.getSettingsModule(module)) {
                            if (modulesMapTemp.getAsJsonObject().get(setting.getId()) != null) {
                                if (setting.getType() == "intslider") {
                                    setting.setValueInt(modulesMapTemp.getAsJsonObject().get(setting.getId()).getAsInt());
                                } else if (setting.getType() == "doubleslider") {
                                    setting.setValueDouble(modulesMapTemp.getAsJsonObject().get(setting.getId()).getAsDouble());
                                } else if (setting.getType() == "boolean") {
                                    setting.setValBoolean(modulesMapTemp.getAsJsonObject().get(setting.getId()).getAsBoolean());
                                } else if (setting.getType() == "mode") {
                                    setting.setValueString(modulesMapTemp.getAsJsonObject().get(setting.getId()).getAsString());
                                }
                            }
                        }
                        */

                    }
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("[" + Ferox.NAME_VERSION + "]" + " " + "Config loading broke. Oops!");
        }
    }

    public void init() {
        if (new File("FeroxConfig.json").exists()) {
            System.out.println(Ferox.NAME_VERSION + " " + "Loading config!");
            loadConfig();
        } else {
            System.out.println(Ferox.NAME_VERSION + " " + "Saving config!");
            saveConfig();
        }
    }
}
