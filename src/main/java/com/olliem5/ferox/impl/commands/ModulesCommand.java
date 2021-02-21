package com.olliem5.ferox.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author olliem5
 */

public final class ModulesCommand extends AbstractCommand {
    public ModulesCommand() {
        super("Lists all the client's modules", "modules/mods", "modules", "mods");
    }

    @Override
    public void execute(String[] args) {
        for (Module module : ModuleManager.getModules()) {
            MessageUtil.sendClientMessage(ChatFormatting.AQUA + module.getName() + " " + ChatFormatting.GRAY + ": " + ChatFormatting.WHITE + module.getDescription());
        }
    }
}
