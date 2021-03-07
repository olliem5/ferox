package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;
import org.lwjgl.input.Keyboard;

/**
 * @author Gav06
 */

public final class BindCommand extends AbstractCommand {
    public BindCommand() {
        super("Binds a module to a specified key", "bind/b [module] [key]", "bind", "b");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            MessageUtil.sendClientMessage("Please specify a module");
            return;
        }

        if (args.length == 1) {
            MessageUtil.sendClientMessage("Please specify a key");
            return;
        }

        Module module = ModuleManager.getModuleByName(args[0]);

        if (module == null) {
            MessageUtil.sendClientMessage("Could not find module \"" + args[0] + "\"");
            return;
        }

        String newKey = args[1].toUpperCase();

        module.setKey(Keyboard.getKeyIndex(newKey));
        MessageUtil.sendClientMessage("Bound " + module.getName() + " to " + newKey);
    }
}
