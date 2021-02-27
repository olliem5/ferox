package com.olliem5.ferox.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author olliem5
 */

public final class DrawnCommand extends AbstractCommand {
    public DrawnCommand() {
        super("Makes a module drawn on the ArrayList", "drawn/d [module name]", "drawn", "d");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        for (String string : args) {
            for (Module module : ModuleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(string) && !module.isDrawn()) {
                    MessageUtil.sendClientMessage(ChatFormatting.AQUA + module.getName() + ChatFormatting.RESET + " is now " + ChatFormatting.GREEN + "DRAWN");
                    module.setDrawn(true);
                } else if (module.getName().equalsIgnoreCase(string) && module.isDrawn()) {
                    MessageUtil.sendClientMessage(ChatFormatting.AQUA + module.getName() + ChatFormatting.RESET + " is now " + ChatFormatting.RED + "HIDDEN");
                    module.setDrawn(false);
                }
            }
        }
    }
}
