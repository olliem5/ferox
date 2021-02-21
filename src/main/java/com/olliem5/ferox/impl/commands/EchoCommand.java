package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * @author olliem5
 * @author Reap
 */

public final class EchoCommand extends AbstractCommand {
    public EchoCommand() {
        super("Echos something to the console", "echo/say/tell [message]", "echo", "say", "tell");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        StringBuilder stringBuilder = new StringBuilder();

        for (String string : args) {
            stringBuilder.append(string).append(" ");
        }

        MessageUtil.sendClientMessage(stringBuilder.toString());
    }
}
