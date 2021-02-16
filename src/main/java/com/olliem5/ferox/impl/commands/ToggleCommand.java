package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author olliem5
 */

public final class ToggleCommand extends AbstractCommand {
    public ToggleCommand() {
        super("Toggles a specified module.", "toggle/t/ <module name>", "toggle", "t");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        for (String string : args) {
            for (Module module : ModuleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(string)) {
                    module.toggle();
                }
            }
        }
    }
}
