package me.olliem5.ferox.api.module;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModuleManager {
    private static ArrayList<Module> modules = new ArrayList<>();

    public static void init() {
        Reflections reflections = new Reflections("me.olliem5.ferox.impl.modules");

        reflections.getSubTypesOf(Module.class).forEach(clazz -> {

            try {
                Module module = clazz.newInstance();
                modules.add(module);
            } catch (InstantiationException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesInCategory(Category category) {
        ArrayList<Module> modulesInCategory = new ArrayList<>();

        for (Module module : modules) {
            if (module.getCategory().equals(category)) {
                modulesInCategory.add(module);
            }
        }

        return modulesInCategory;
    }

    public static Module getModuleByName(String name) {
        return modules.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
