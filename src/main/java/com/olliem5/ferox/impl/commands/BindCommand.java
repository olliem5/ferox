package com.olliem5.ferox.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
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
            MessageUtil.sendClientMessage("Please specify a module.");
            return;
        }

        if (args.length == 1) {
            MessageUtil.sendClientMessage("Please specify a key.");
            return;
        }

        if (ModuleManager.getModuleByName(args[0]) == null) {
            MessageUtil.sendClientMessage("Could not find module " + ChatFormatting.AQUA + args[0] + ChatFormatting.RESET + ".");
            return;
        }

        ModuleManager.getModuleByName(args[0]).setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
        MessageUtil.sendClientMessage("Bound " + ChatFormatting.AQUA + ModuleManager.getModuleByName(args[0]).getName() + ChatFormatting.RESET + " to " + ChatFormatting.GREEN + args[1].toUpperCase() + ChatFormatting.RESET + ".");
    }
}
