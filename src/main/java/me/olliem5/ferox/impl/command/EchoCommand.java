package me.olliem5.ferox.impl.command;

import me.yagel15637.venture.command.AbstractCommand;
import me.olliem5.ferox.api.util.client.MessageUtil;

public class EchoCommand extends AbstractCommand {
    public EchoCommand() {
        super("Echos something to the console.", "say/echo/tell <message>", "say", "echo", "tell");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        StringBuilder msg = new StringBuilder();

        for (String string : args) {
            msg.append(string).append(" ");
        }

        MessageUtil.sendClientMessage(msg.toString());
    }
}
