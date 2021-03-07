package com.olliem5.ferox.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author Gav06
 */

public final class PrefixCommand extends AbstractCommand {
    public PrefixCommand() {
        super("Changes Ferox's command prefix", "prefix [anything]", "prefix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            MessageUtil.sendClientMessage("Please specify a prefix");
            return;
        }

        Ferox.CHAT_PREFIX = args[0];

        MessageUtil.sendClientMessage("Set the chat prefix to " + ChatFormatting.AQUA + args[0] + ChatFormatting.RESET + ".");
    }
}
