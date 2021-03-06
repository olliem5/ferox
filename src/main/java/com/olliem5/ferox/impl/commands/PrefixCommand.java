package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.Ferox;
import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

public class PrefixCommand extends AbstractCommand {
    public PrefixCommand() {
        super("change the chat prefix of the command system", "prefix [anything]", "prefix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            MessageUtil.sendClientMessage("Please specify a prefix");
            return;
        }

        Ferox.CHAT_PREFIX = args[0];
        MessageUtil.sendClientMessage("Set the chat prefix to \"" + args[0] + "\"");
    }
}
