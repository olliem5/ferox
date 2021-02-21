package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.api.util.client.ConfigUtil;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author olliem5
 */

public final class ConfigCommand extends AbstractCommand {
    public ConfigCommand() {
        super("Saves or loads your config", "config/c [save/load]", "config", "c");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        for (String string : args) {
            if (string.equalsIgnoreCase("save")) {
                ConfigUtil.saveConfig();

                MessageUtil.sendClientMessage("Saved your config!");
            }

            //TODO: Fix this
//            if (string.equalsIgnoreCase("Load")) {
//                ConfigUtil.loadConfig();
//
//                MessageUtil.sendClientMessage("Loaded your config!");
//            }
        }
    }
}
