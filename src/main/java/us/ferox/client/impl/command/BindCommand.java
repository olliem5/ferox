package us.ferox.client.impl.command;

import me.yagel15637.venture.command.AbstractCommand;
import us.ferox.client.api.util.client.MessageUtil;

public class BindCommand extends AbstractCommand {
    public BindCommand() {
        super("Binds a specified module to a key", "<b/bind> <module name> <key>", "b", "bind");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        StringBuilder msg = new StringBuilder();
        for (String s : args) msg.append(s).append(" ");
        MessageUtil.sendClientMessage(msg.toString());
    }
}
