package me.olliem5.ferox.impl.command;

import me.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

public final class EchoCommand extends AbstractCommand {
    public EchoCommand() {
        super("Echos something to the console.", "say/echo/tell <message>", "say", "echo", "tell");
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
